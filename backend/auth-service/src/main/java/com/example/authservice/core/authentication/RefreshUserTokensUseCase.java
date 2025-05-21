package com.example.authservice.core.authentication;

import com.example.authservice.domain.i18n.MessageKey;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.model.token.Auth;
import com.example.authservice.domain.model.token.TokenInfo;
import com.example.authservice.domain.ports.out.MessageProviderPort;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RefreshUserTokensUseCase {
    private final MessageProviderPort messageProvider;
    private final TokenService tokenService;

    Auth execute(TokenInfo tokenInfo) {
        return tokenService.findTokenByHash(tokenInfo)
                .map(t -> tokenService.generateNewAuth(new UserId(t.getUserId())))
                .orElseThrow(() -> new JwtException(messageProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }
}
