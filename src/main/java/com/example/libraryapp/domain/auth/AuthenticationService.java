package com.example.libraryapp.domain.auth;

import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.card.LibraryCardRepository;
import com.example.libraryapp.domain.helper.CardNumGenerator;
import com.example.libraryapp.domain.token.JwtService;
import com.example.libraryapp.domain.token.Token;
import com.example.libraryapp.domain.token.TokenRepository;
import com.example.libraryapp.domain.token.TokenType;
import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LibraryCardRepository cardRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        saveUserLibraryCard(savedUser);
        saveUserToken(savedUser, jwtToken);
        AuthenticationResponse response = createAuthResponse(savedUser, jwtToken);
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        AuthenticationResponse response = createAuthResponse(user, jwtToken);
        return response;
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveUserLibraryCard(User user) {
        LibraryCard libraryCard = LibraryCard.builder()
                .active(true)
                .barcode(CardNumGenerator.generate(user.getId()))
                .issuedAt(LocalDateTime.now())
                .build();
        cardRepository.save(libraryCard);

        user.setCard(libraryCard);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private AuthenticationResponse createAuthResponse(User user, String token) {
        return AuthenticationResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .card(user.getCard())
                .token(token)
                .build();
    }
}