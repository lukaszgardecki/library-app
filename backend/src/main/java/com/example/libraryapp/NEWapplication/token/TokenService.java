package com.example.libraryapp.NEWapplication.token;

import com.example.libraryapp.NEWdomain.token.dto.AuthTokensDto;
import com.example.libraryapp.NEWdomain.token.model.Token;
import com.example.libraryapp.NEWdomain.token.ports.AccessTokenRepository;
import com.example.libraryapp.NEWdomain.token.ports.RefreshTokenRepository;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class TokenService {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenGenerator generator;

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
        List<Token> validUserAccessTokens = accessTokenRepository.findAllValidTokenByUser(userId);
        List<Token> validUserRefreshTokens = refreshTokenRepository.findAllValidTokenByUser(userId);

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
