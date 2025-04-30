package com.example.authservice.core.authentication;

import com.example.authservice.domain.MessageKey;
import com.example.authservice.domain.model.token.TokenType;
import com.example.authservice.domain.ports.MessageProviderPort;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class HttpRequestExtractor {
    private final MessageProviderPort msgProvider;

    String extractTokenFromHeader(String authHeader) {
        String BEARER_PREFIX = TokenType.BEARER.getPrefix();
        return Optional.ofNullable(authHeader)
                .filter(header -> header.startsWith(BEARER_PREFIX))
                .map(header -> header.substring(BEARER_PREFIX.length()))
                .orElseThrow(() -> new JwtException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }
}
