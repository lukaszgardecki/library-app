package com.example.authservice.token.core;

import com.example.authservice.auth.domain.dto.UserAuthDto;
import com.example.authservice.token.domain.dto.TokenAuth;
import com.example.authservice.token.domain.exceptions.TokenNotFoundException;
import com.example.authservice.token.domain.model.Token;
import com.example.authservice.token.domain.ports.AccessTokenRepositoryPort;
import com.example.authservice.token.domain.ports.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class TokenService {
    private final AccessTokenRepositoryPort accessTokenRepository;
    private final RefreshTokenRepositoryPort refreshTokenRepository;
    private final TokenGenerator generator;

    Token getTokenByHash(String hash) {
        return accessTokenRepository.findByToken(hash)
                .or(() -> refreshTokenRepository.findByToken(hash))
                .orElseThrow(() -> new TokenNotFoundException(hash));
    }

    TokenAuth generateNewTokensFor(UserAuthDto userAuth) {
        TokenAuth tokenAuth = generator.generateTokenAuth(userAuth);
        revokeUserTokens(userAuth.userId());
        saveTokens(
                new Token(userAuth.userId(), tokenAuth.accessToken()),
                new Token(userAuth.userId(), tokenAuth.refreshToken())
        );
        return tokenAuth;
    }

    void revokeUserTokens(Long userId) {
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
