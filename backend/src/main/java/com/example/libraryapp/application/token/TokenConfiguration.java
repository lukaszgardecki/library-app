package com.example.libraryapp.application.token;

import com.example.libraryapp.application.user.UserConfiguration;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.token.ports.AccessTokenRepositoryPort;
import com.example.libraryapp.domain.token.ports.RefreshTokenRepositoryPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryAccessTokenRepositoryAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryRefreshTokenRepositoryAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryUserRepositoryAdapter;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class TokenConfiguration {

    public TokenFacade tokenFacade() {
        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
        AccessTokenRepositoryPort accessTokenRepository = new InMemoryAccessTokenRepositoryAdapter();
        RefreshTokenRepositoryPort refreshTokenRepository = new InMemoryRefreshTokenRepositoryAdapter();
        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
        Key secret = getSigningKey("uiadfhguiajefhdgjuiouiodfghuioasdfghauopdfhguoahfdughafdogha");
        TokenGenerator tokenGenerator = new TokenGenerator(secret);
        TokenValidator tokenValidator = new TokenValidator(secret);
        TokenService tokenService = new TokenService(accessTokenRepository, refreshTokenRepository, tokenGenerator);
        HttpRequestExtractor extractor = new HttpRequestExtractor();
        return new TokenFacade(
                new GenerateUserTokensUseCase(tokenService),
                new RefreshUserTokensUseCase(tokenService, userFacade, tokenValidator),
                new RevokeUserTokensUseCase(tokenService),
                new ValidateTokenAndFingerprintUseCase(accessTokenRepository, refreshTokenRepository, tokenValidator),
                new ExtractTokenFromHeaderUseCase(extractor),
                new ExtractFingerprintFromHeaderUseCase(extractor),
                new GetUsernameFromTokenUseCase(tokenValidator),
                new GetTokenByHashUseCase(tokenService)
        );
    }

    @Bean
    TokenFacade tokenFacade(
            UserFacade userFacade,
            AccessTokenRepositoryPort accessTokenRepository,
            RefreshTokenRepositoryPort refreshTokenRepository,
            TokenValidator tokenValidator,
            TokenGenerator tokenGenerator
    ) {
        HttpRequestExtractor extractor = new HttpRequestExtractor();
        TokenService tokenService = new TokenService(accessTokenRepository, refreshTokenRepository, tokenGenerator);
        return new TokenFacade(
                new GenerateUserTokensUseCase(tokenService),
                new RefreshUserTokensUseCase(tokenService, userFacade, tokenValidator),
                new RevokeUserTokensUseCase(tokenService),
                new ValidateTokenAndFingerprintUseCase(accessTokenRepository, refreshTokenRepository, tokenValidator),
                new ExtractTokenFromHeaderUseCase(extractor),
                new ExtractFingerprintFromHeaderUseCase(extractor),
                new GetUsernameFromTokenUseCase(tokenValidator),
                new GetTokenByHashUseCase(tokenService)
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
    public Key getSigningKey(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
