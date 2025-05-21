package com.example.authservice.core.authentication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CookieValidator {
    private final TokenParser tokenParser;

    boolean validate(String token, String cookieValue) {
        String tokenFgp = tokenParser.extractBodyClaims(token, claims -> claims.get(CookieValueGenerator.FINGERPRINT_NAME)).toString();
        String requestFgp = TokenUtils.hashWithSHA256(cookieValue);
        return tokenFgp.equals(requestFgp);
    }
}
