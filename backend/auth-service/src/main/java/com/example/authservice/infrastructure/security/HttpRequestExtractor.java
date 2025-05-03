package com.example.authservice.infrastructure.security;

import com.example.authservice.domain.Constants;
import com.example.authservice.domain.MessageKey;
import com.example.authservice.domain.ports.MessageProviderPort;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HttpRequestExtractor {
    private final MessageProviderPort msgProvider;

    public String extractTokenFromRequest(HttpServletRequest request) {
        String BEARER_PREFIX = Constants.BEARER_PREFIX;
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER_PREFIX))
                .map(authHeader -> authHeader.substring(BEARER_PREFIX.length()))
                .orElseThrow(() -> new JwtException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }

    public String extractToken(String authHeader) {
        String BEARER_PREFIX = Constants.BEARER_PREFIX;
        return Optional.ofNullable(authHeader)
                .filter(header -> header.startsWith(BEARER_PREFIX))
                .map(header -> header.substring(BEARER_PREFIX.length()))
                .orElseThrow(() -> new JwtException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }

    public String extractAuthCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> Constants.AUTH_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new JwtException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }
}
