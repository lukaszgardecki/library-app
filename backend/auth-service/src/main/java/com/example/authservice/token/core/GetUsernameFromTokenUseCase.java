package com.example.authservice.token.core;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUsernameFromTokenUseCase {
    private final TokenValidator tokenValidator;

    String execute(String token) {
        return tokenValidator.extractBodyClaims(token, Claims::getSubject);
    }
}
