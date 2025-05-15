package com.example.authservice.core.authentication;

import com.example.authservice.domain.i18n.MessageKey;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.model.token.TokenInfo;
import com.example.authservice.domain.ports.out.MessageProviderPort;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class ValidateTokenAndCookieUseCase {
    private final MessageProviderPort msgProvider;
    private final TokenService tokenService;
    private final TokenValidator tokenValidator;
    private final CookieValidator cookieValidator;

    UserId execute(TokenInfo tokenInfo, String cookie) {
        return Optional.of(tokenInfo)
                .filter(t -> t.hash() != null)
                .filter(t -> cookie != null)
                .filter(t -> cookieValidator.validate(t.hash(), cookie))
                .filter(t -> tokenValidator.validate(t.hash()))
                .flatMap(t -> tokenService.findTokenByHash(tokenInfo))
                .map(t -> new UserId(t.getUserId()))
                .orElseThrow(() -> new JwtException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }

    UserId execute(TokenInfo tokenInfo) {
        return Optional.of(tokenInfo)
                .filter(t -> t.hash() != null)
                .filter(t -> tokenValidator.validate(t.hash()))
                .flatMap(t -> tokenService.findTokenByHash(tokenInfo))
                .map(t -> new UserId(t.getUserId()))
                .orElseThrow(() -> new JwtException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }
}
