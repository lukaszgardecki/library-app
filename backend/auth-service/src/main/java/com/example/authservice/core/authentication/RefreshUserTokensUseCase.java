package com.example.authservice.core.authentication;

import com.example.authservice.domain.MessageKey;
import com.example.authservice.domain.dto.token.TokenInfoDto;
import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.model.token.Auth;
import com.example.authservice.domain.ports.MessageProviderPort;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RefreshUserTokensUseCase {
    private final MessageProviderPort messageProvider;
    private final TokenService tokenService;

    Auth execute(TokenInfoDto tokenInfo) {
        return tokenService.findTokenByHash(tokenInfo)
                .map(t -> tokenService.generateNewAuth(new UserId(t.getUserId())))
                .orElseThrow(() -> new JwtException(messageProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }
}
