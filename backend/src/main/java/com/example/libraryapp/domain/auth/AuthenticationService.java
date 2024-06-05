package com.example.libraryapp.domain.auth;

import com.example.libraryapp.domain.card.CardStatus;
import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.config.AuthTokens;
import com.example.libraryapp.domain.config.Fingerprint;
import com.example.libraryapp.domain.exception.auth.ForbiddenAccessException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.helper.LibraryGenerator;
import com.example.libraryapp.domain.member.*;
import com.example.libraryapp.domain.token.TokenService;
import com.example.libraryapp.management.Message;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        checkIfEmailIsUnique(request);
        Member member = createMemberWithUserRole(request);
        Member savedMember = memberRepository.saveAndFlush(member);
        LibraryCard card = createMemberLibraryCard(savedMember);
        savedMember.setCard(card);
    }


    public LoginResponse authenticate(
            LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        Member member = memberRepository.findByEmail(loginRequest.getUsername())
                .orElseThrow(() -> new BadCredentialsException(Message.BAD_CREDENTIALS));
        AuthTokens auth = tokenService.generateAuth(member);
        String accessToken = auth.accessToken();
        String refreshToken = auth.refreshToken();
        Fingerprint fingerprint = auth.fingerprint();
        Cookie fgpCookie = fingerprint.getCookie();

        tokenService.revokeAllUserTokens(member);
        tokenService.saveTokens(member, accessToken, refreshToken);
        response.addHeader(fgpCookie.getName(), fgpCookie.getValue());
        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = tokenService.findToken(request)
                .orElseThrow(() -> new AccessDeniedException(Message.ACCESS_DENIED));
        String memberEmail = tokenService.extractUsername(refreshToken);
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(MemberNotFoundException::new);

        AuthTokens auth = tokenService.generateAuth(member);
        String newAccessToken = auth.accessToken();
        String newRefreshToken = auth.refreshToken();
        Fingerprint newFingerprint = auth.fingerprint();
        Cookie fgpCookie = newFingerprint.getCookie();
        tokenService.revokeAllUserTokens(member);
        tokenService.saveTokens(member, newAccessToken, newRefreshToken);
        response.addHeader(fgpCookie.getName(), fgpCookie.getValue());
        return new LoginResponse(newAccessToken, newRefreshToken);
    }

    public boolean checkIfCurrentLoggedInUserIsAdmin() {
        return getCurrentLoggedInUserRole().equals(Role.ADMIN.name());
    }

    public void checkIfAdminRequested() {
        boolean isAdmin = checkIfCurrentLoggedInUserIsAdmin();
        boolean isNotAdmin = !isAdmin;
        if (isNotAdmin) throw new ForbiddenAccessException();
    }

    public void checkIfAdminOrDataOwnerRequested(Long userId) {
        boolean isOwner = Objects.equals(getCurrentLoggedInUserId(), userId);
        boolean isAdmin = checkIfCurrentLoggedInUserIsAdmin();
        boolean isNotAdminOrDataOwner = !(isOwner || isAdmin);
        if (isNotAdminOrDataOwner) throw new ForbiddenAccessException();
    }

    private String getCurrentLoggedInUserRole() {
        Long userId = getCurrentLoggedInUserId();
        return findMemberRoleByUserId(userId)
                .orElseThrow(() -> new MemberNotFoundException(userId));
    }

    private Long getCurrentLoggedInUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findMemberByEmail(username).getId();
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    private Optional<String> findMemberRoleByUserId(Long id) {
        return memberRepository.findById(id)
                .map(user -> user.getRole().name());
    }

    private LibraryCard createMemberLibraryCard(Member member) {
        return LibraryCard.builder()
                .status(CardStatus.ACTIVE)
                .barcode(LibraryGenerator.generateCardNum(member.getId()))
                .issuedAt(LocalDateTime.now())
                .build();
    }

    private Member createMemberWithUserRole(RegisterRequest request) {
        Address address = createAddressFromRequest(request);
        Person person = createPersonFromRequest(request);
        person.setAddress(address);
        Member member = new Member();
        member.setCharge(BigDecimal.ZERO);
        member.setPerson(person);
        member.setDateOfMembership(LocalDate.now());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setStatus(AccountStatus.PENDING);
        member.setRole(Role.USER);
        return member;
    }

    private Address createAddressFromRequest(RegisterRequest request) {
        return Address.builder()
                .streetAddress(request.getStreetAddress())
                .zipCode(request.getZipCode())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .build();
    }

    private Person createPersonFromRequest(RegisterRequest request) {
        return Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .build();
    }

    private void checkIfEmailIsUnique(RegisterRequest request) {
        memberRepository.findAll().stream()
                .filter(member -> member.getEmail().equals(request.getEmail()))
                .findAny()
                .ifPresent(email -> {
                    throw new BadCredentialsException(Message.BAD_EMAIL);
                });
    }
}