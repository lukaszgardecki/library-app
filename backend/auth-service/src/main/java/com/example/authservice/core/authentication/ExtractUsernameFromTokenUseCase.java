package com.example.authservice.core.authentication;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ExtractUsernameFromTokenUseCase {
    private final TokenParser tokenParser;

    String execute(String token) {
        return tokenParser.extractBodyClaims(token, Claims::getSubject);
    }
}
