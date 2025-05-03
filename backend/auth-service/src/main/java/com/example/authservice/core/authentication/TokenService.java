package com.example.authservice.core.authentication;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.dto.token.TokenInfoDto;
import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.model.token.Auth;
import com.example.authservice.domain.model.token.CookieValues;
import com.example.authservice.domain.model.token.Token;
import com.example.authservice.domain.ports.AccessTokenRepositoryPort;
import com.example.authservice.domain.ports.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class TokenService {
    private final AccessTokenRepositoryPort accessTokenRepository;
    private final RefreshTokenRepositoryPort refreshTokenRepository;
    private final AuthDetailsFacade authDetailsFacade;
    private final TokenGenerator generator;

    Optional<Token> findTokenByHash(TokenInfoDto tokenInfo) {
        return switch (tokenInfo.type()) {
            case ACCESS -> accessTokenRepository.findByToken(tokenInfo.hash());
            case REFRESH -> refreshTokenRepository.findByToken(tokenInfo.hash());
        };
    }

    Auth generateNewAuth(UserId userId) {
        CookieValues cookieValues = CookieValueGenerator.generate();
        AuthDetailsDto authDetails = authDetailsFacade.getAuthDetailsByUserId(userId);
        Token accessToken = generator.generateAccessToken(authDetails, cookieValues);
        Token refreshToken = generator.generateRefreshToken(authDetails, cookieValues);
        revokeUserTokens(new UserId(authDetails.userId()));
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
