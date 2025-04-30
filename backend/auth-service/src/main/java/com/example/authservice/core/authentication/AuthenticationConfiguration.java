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

//    public AuthenticationFacade authenticationFacade() {
//        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
//        AuthenticationManagerPort authenticationManager = new InMemoryAuthenticationManagerPortAdapter(userRepository);
//        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
//        TokenFacade tokenFacade = new TokenConfiguration().tokenFacade();
//        PersonFacade personFacade = new PersonConfiguration().personFacade();
//        return new AuthenticationFacade(
//                new AuthenticateUserUseCase(userFacade, personFacade, tokenFacade, authenticationManager, null),
//                new GetCurrentLoggedInUserUseCase(userFacade,authenticationManager)
//        );
//    }

//    public AuthenticationFacade authenticationFacade(UserRepositoryPort userRepository) {
//        AuthenticationManagerPort authenticationManager = new InMemoryAuthenticationManagerPortAdapter(userRepository);
//        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
//        TokenFacade tokenFacade = new TokenConfiguration().tokenFacade();
//        PersonFacade personFacade = new PersonConfiguration().personFacade();
//        return new AuthenticationFacade(
//                new AuthenticateUserUseCase(userFacade, personFacade, tokenFacade, authenticationManager, null),
//                new GetCurrentLoggedInUserUseCase(userFacade,authenticationManager)
//        );
//    }

    @Bean
    AuthenticationFacade authenticationFacade(
            AuthenticationManagerPort authenticationManager,
            AccessTokenRepositoryPort accessTokenRepository,
            RefreshTokenRepositoryPort refreshTokenRepository,
            MessageProviderPort msgProvider,
            EventPublisherPort publisher,
            AuthDetailsFacade authDetailsFacade,
            TokenGenerator tokenGenerator,
            AuthValidator validator
    ) {
        AuthenticationService authenticationService = new AuthenticationService(authDetailsFacade);
        TokenService tokenService = new TokenService(
                accessTokenRepository, refreshTokenRepository, authDetailsFacade, tokenGenerator
        );

        return new AuthenticationFacade(
                new AuthenticateUserUseCase(authenticationService, tokenService, authenticationManager, publisher),
                new RefreshUserTokensUseCase(msgProvider, tokenService),
                new ValidateTokenAndCookieUseCase(msgProvider, tokenService, validator),
                new ExtractTokenFromHeaderUseCase(new HttpRequestExtractor(msgProvider))
        );
    }

    @Bean
    TokenGenerator tokenGenerator(Key key) {
        return new TokenGenerator(key);
    }

    @Bean
    AuthValidator authValidator(Key key) {
        return new AuthValidator(key);
    }

    @Bean
    public Key getSigningKey(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
