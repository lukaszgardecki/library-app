package com.example.authservice.core.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.function.Function;

@RequiredArgsConstructor
class TokenParser {
    private final Key signingKey;

    Jws<Claims> extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
    }

    <T> T extractBodyClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token).getBody();
        return claimsResolver.apply(claims);
    }

    <T> T extractHeader(String token, Function<JwsHeader, T> claimsResolver) {
        JwsHeader header = extractAllClaims(token).getHeader();
        return claimsResolver.apply(header);
    }
}
