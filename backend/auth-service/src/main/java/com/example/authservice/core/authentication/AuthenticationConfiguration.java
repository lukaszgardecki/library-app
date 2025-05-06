package com.example.authservice.core.authentication;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.ports.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    AuthenticationFacade authenticationFacade(
            AuthenticationManagerPort authenticationManager,
            AccessTokenRepositoryPort accessTokenRepository,
            RefreshTokenRepositoryPort refreshTokenRepository,
            MessageProviderPort msgProvider,
            EventPublisherPort publisher,
            AuthDetailsFacade authDetailsFacade,
            TokenGenerator tokenGenerator,
            Key key
    ) {
        TokenParser tokenParser = new TokenParser(key);
        AuthenticationService authenticationService = new AuthenticationService(authDetailsFacade);
        TokenService tokenService = new TokenService(
                accessTokenRepository, refreshTokenRepository, authDetailsFacade, tokenGenerator
        );

        return new AuthenticationFacade(
                new AuthenticateUserUseCase(
                        authenticationManager, authenticationService, msgProvider, publisher, tokenService
                ),
                new RefreshUserTokensUseCase(msgProvider, tokenService),
                new ValidateTokenAndCookieUseCase(
                        msgProvider, tokenService, new TokenValidator(tokenParser), new CookieValidator(tokenParser)
                ),
                new ExtractUsernameFromTokenUseCase(tokenParser),
                new RevokeUserTokensUseCase(tokenService, publisher)
        );
    }

    @Bean
    TokenGenerator tokenGenerator(
            @Value("${jwt.expirationTime}") long accessTokenExpTime,
            @Value("${jwt.refresh-token.expiration}") long refreshTokenExpTime,
            Key key
    ) {
        return new TokenGenerator(key, accessTokenExpTime, refreshTokenExpTime);
    }

    @Bean
    public Key getSigningKey(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
