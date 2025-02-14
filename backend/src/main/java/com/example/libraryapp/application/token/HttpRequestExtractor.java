package com.example.libraryapp.application.token;


import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.application.message.MessageFacade;
import com.example.libraryapp.domain.token.model.TokenType;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class HttpRequestExtractor {
    private final MessageFacade messageFacade;

    public String extractTokenFromHeader(HttpServletRequest request) {
        String BEARER_PREFIX = TokenType.BEARER.getPrefix();
        return Optional.of(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER_PREFIX))
                .map(authHeader -> authHeader.substring(BEARER_PREFIX.length()))
                .orElseThrow(() -> new JwtException(messageFacade.get(MessageKey.ACCESS_DENIED)));
    }

    public String extractFingerprintFromHeader(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> FingerprintGenerator.FINGERPRINT_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new JwtException(messageFacade.get(MessageKey.ACCESS_DENIED)));
    }
}
