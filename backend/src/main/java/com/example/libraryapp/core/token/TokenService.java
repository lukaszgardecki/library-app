package com.example.libraryapp.core.token;

import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.token.exceptions.TokenNotFoundException;
import com.example.libraryapp.domain.token.model.Token;
import com.example.libraryapp.domain.token.ports.AccessTokenRepositoryPort;
import com.example.libraryapp.domain.token.ports.RefreshTokenRepositoryPort;
import com.example.libraryapp.domain.user.dto.UserDto;
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

    AuthTokensDto generateNewTokensFor(UserDto user) {
        AuthTokensDto auth = generator.generateAuth(user);
        revokeUserTokens(user.getId());
        saveTokens(
                new Token(user.getId(), auth.accessToken()),
                new Token(user.getId(), auth.refreshToken())
        );
        return auth;
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
