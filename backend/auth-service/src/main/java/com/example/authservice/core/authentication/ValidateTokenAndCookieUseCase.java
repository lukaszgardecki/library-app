package com.example.authservice.core.authentication;

import com.example.authservice.domain.MessageKey;
import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.ports.MessageProviderPort;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class ValidateTokenAndCookieUseCase {
    private final MessageProviderPort msgProvider;
    private final TokenService tokenService;
    private final AuthValidator validator;

    UserId execute(String token, String fingerprint) {
        return Optional.ofNullable(token)
                .filter(t -> fingerprint != null)
                .filter(t -> validator.validateFgp(t, fingerprint))
                .filter(validator::validateToken)
                .flatMap(tokenService::findAccessTokenByHash)
                .map(t -> new UserId(t.getUserId()))
                .orElseThrow(() -> new JwtException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }
}
