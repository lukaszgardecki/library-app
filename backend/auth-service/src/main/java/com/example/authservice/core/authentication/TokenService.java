package com.example.authservice.core.authentication;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.model.token.Auth;
import com.example.authservice.domain.model.token.CookieValues;
import com.example.authservice.domain.model.token.Token;
import com.example.authservice.domain.model.token.TokenInfo;
import com.example.authservice.domain.ports.out.AccessTokenRepositoryPort;
import com.example.authservice.domain.ports.out.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class TokenService {
    private final AccessTokenRepositoryPort accessTokenRepository;
    private final RefreshTokenRepositoryPort refreshTokenRepository;
    private final AuthDetailsFacade authDetailsFacade;
    private final TokenGenerator generator;

    Optional<Token> findTokenByHash(TokenInfo tokenInfo) {
        return switch (tokenInfo.type()) {
            case ACCESS -> accessTokenRepository.findByToken(tokenInfo.hash());
            case REFRESH -> refreshTokenRepository.findByToken(tokenInfo.hash());
        };
    }

    Auth generateNewAuth(UserId userId) {
        CookieValues cookieValues = CookieValueGenerator.generate();
        AuthDetails authDetails = authDetailsFacade.getAuthDetailsByUserId(userId);
        Token accessToken = generator.generateAccessToken(authDetails, cookieValues);
        Token refreshToken = generator.generateRefreshToken(authDetails, cookieValues);
        revokeUserTokens(authDetails.getUserId());
        saveTokens(accessToken, refreshToken);
        return new Auth(accessToken, refreshToken, cookieValues);
    }

    void revokeUserTokens(UserId userId) {
        List<Token> validUserAccessTokens = accessTokenRepository.findAllValidTokensByUserId(userId);
        List<Token> validUserRefreshTokens = refreshTokenRepository.findAllValidTokensByUserId(userId);

        if (validUserAccessTokens.isEmpty() && validUserRefreshTokens.isEmpty()) return;

        validUserAccessTokens.forEach(Token::setInvalid);
        validUserRefreshTokens.forEach(Token::setInvalid);
        accessTokenRepository.saveAll(validUserAccessTokens);
        refreshTokenRepository.saveAll(validUserRefreshTokens);
    }

    private void saveTokens(Token accessToken, Token refreshToken) {
        accessTokenRepository.save(accessToken);
        refreshTokenRepository.save(refreshToken);
    }
}
