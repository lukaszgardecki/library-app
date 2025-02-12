package com.example.libraryapp.application.token;

import com.example.libraryapp.application.message.MessageFacade;
import com.example.libraryapp.application.user.UserConfiguration;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.token.ports.AccessTokenRepository;
import com.example.libraryapp.domain.token.ports.RefreshTokenRepository;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryAccessTokenRepositoryImpl;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryRefreshTokenRepositoryImpl;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryUserRepositoryImpl;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class TokenConfiguration {

    public TokenFacade tokenFacade() {
        InMemoryUserRepositoryImpl userRepository = new InMemoryUserRepositoryImpl();
        AccessTokenRepository accessTokenRepository = new InMemoryAccessTokenRepositoryImpl();
        RefreshTokenRepository refreshTokenRepository = new InMemoryRefreshTokenRepositoryImpl();
        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
        Key secret = getSigningKey("uiadfhguiajefhdgjuiouiodfghuioasdfghauopdfhguoahfdughafdogha");
        TokenGenerator tokenGenerator = new TokenGenerator(secret);
        TokenValidator tokenValidator = new TokenValidator(secret);
        TokenService tokenService = new TokenService(accessTokenRepository, refreshTokenRepository, tokenGenerator);
        return new TokenFacade(
                new GenerateUserTokensUseCase(tokenService),
                new RefreshUserTokensUseCase(tokenService, userFacade, tokenValidator),
                new RevokeUserTokensUseCase(tokenService),
                new ValidateTokenAndFingerprintUseCase(accessTokenRepository, refreshTokenRepository, tokenValidator),
                new GetUsernameFromTokenUseCase(tokenValidator)
        );
    }

    @Bean
    TokenFacade tokenFacade(
            UserFacade userFacade,
            AccessTokenRepository accessTokenRepository,
            RefreshTokenRepository refreshTokenRepository,
            TokenValidator tokenValidator,
            TokenGenerator tokenGenerator
    ) {
        TokenService tokenService = new TokenService(accessTokenRepository, refreshTokenRepository, tokenGenerator);
        return new TokenFacade(
                new GenerateUserTokensUseCase(tokenService),
                new RefreshUserTokensUseCase(tokenService, userFacade, tokenValidator),
                new RevokeUserTokensUseCase(tokenService),
                new ValidateTokenAndFingerprintUseCase(accessTokenRepository, refreshTokenRepository, tokenValidator),
                new GetUsernameFromTokenUseCase(tokenValidator)
        );
    }

    @Bean
    TokenGenerator tokenGenerator(Key key) {
        return new TokenGenerator(key);
    }

    @Bean
    TokenValidator tokenValidator(Key key) {
        return new TokenValidator(key);
    }

    @Bean
    HttpRequestExtractor requestExtractor(MessageFacade messageFacade) {
        return new HttpRequestExtractor(messageFacade);
    }

    @Bean
    public Key getSigningKey(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
