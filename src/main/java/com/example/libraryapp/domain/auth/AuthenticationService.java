package com.example.libraryapp.domain.auth;

import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.card.LibraryCardRepository;
import com.example.libraryapp.domain.helper.LibraryGenerator;
import com.example.libraryapp.domain.member.*;
import com.example.libraryapp.domain.token.JwtService;
import com.example.libraryapp.domain.token.Token;
import com.example.libraryapp.domain.token.TokenRepository;
import com.example.libraryapp.domain.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LibraryCardRepository cardRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Member member = createMemberWithUserRole(request);
        Member savedMember = memberRepository.save(member);
        String jwtToken = jwtService.generateToken(member);
        saveMemberLibraryCard(savedMember);
        saveMemberToken(savedMember, jwtToken);
        AuthenticationResponse response = createAuthResponse(savedMember, jwtToken);
        return response;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Member member = memberRepository.findByEmail(request.getUsername())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(member);
        revokeAllUserTokens(member);
        saveMemberToken(member, jwtToken);
        AuthenticationResponse response = createAuthResponse(member, jwtToken);
        return response;
    }


    private void saveMemberToken(Member member, String jwtToken) {
        Token token = Token.builder()
                .member(member)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveMemberLibraryCard(Member member) {
        LibraryCard libraryCard = LibraryCard.builder()
                .active(true)
                .barcode(LibraryGenerator.generateCardNum(member.getId()))
                .issuedAt(LocalDateTime.now())
                .build();
        cardRepository.save(libraryCard);

        member.setCard(libraryCard);
    }

    private void revokeAllUserTokens(Member member) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(member.getId());

        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private Member createMemberWithUserRole(RegisterRequest request) {
        Address address = createAddressFromRequest(request);
        Person person = createPersonFromRequest(request);
        person.setAddress(address);
        Member member = new Member();
        member.setPerson(person);
        member.setDateOfMembership(LocalDate.now());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
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

    private AuthenticationResponse createAuthResponse(Member member, String token) {
        return AuthenticationResponse.builder()
                .id(member.getId())
                .firstName(member.getPerson().getFirstName())
                .lastName(member.getPerson().getLastName())
                .email(member.getEmail())
                .card(member.getCard())
                .token(token)
                .build();
    }
}