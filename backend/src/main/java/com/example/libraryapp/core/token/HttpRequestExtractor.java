package com.example.libraryapp.core.token;


import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.token.model.TokenType;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
class HttpRequestExtractor {

    public String extractTokenFromHeader(HttpServletRequest request) {
        String BEARER_PREFIX = TokenType.BEARER.getPrefix();
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER_PREFIX))
                .map(authHeader -> authHeader.substring(BEARER_PREFIX.length()))
                .orElseThrow(() -> new JwtException(MessageKey.ACCESS_DENIED.name()));
    }

    public String extractFingerprintFromHeader(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> FingerprintGenerator.FINGERPRINT_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new JwtException(MessageKey.ACCESS_DENIED.name()));
    }
}
