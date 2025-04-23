package com.example.authservice.core.token;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
class TokenValidator {
    private final Key signingKey;

    boolean validateToken(String token) {
        return hasAlg(token)
            && hasTypJWT(token)
            && hasSubject(token)
            && hasIssuer(token)
            && hasIssuedDate(token)
            && hasUserID(token)
            && hasUserRole(token)
            && !isExpired(token)
            && !isBeforeDate(token);
    }

    boolean validateFgp(String token, String fingerprint) {
        String tokenFgp = extractBodyClaims(token, claims -> claims.get(FingerprintGenerator.FINGERPRINT_NAME)).toString();
        String requestFgp = TokenUtils.hashWithSHA256(fingerprint);
        return tokenFgp.equals(requestFgp);
    }

    <T> T extractBodyClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token).getBody();
        return claimsResolver.apply(claims);
    }

    private <T> T extractHeader(String token, Function<JwsHeader, T> claimsResolver) {
        JwsHeader header = extractAllClaims(token).getHeader();
        return claimsResolver.apply(header);
    }

    private boolean hasAlg(String token) {
        String expectedAlg = SignatureAlgorithm.HS256.getValue();
        return extractHeader(token, header -> header.get(JwsHeader.ALGORITHM)).toString().equals(expectedAlg);
    }

    private boolean hasTypJWT(String token) {
        String expectedTyp = "JWT";
        return extractHeader(token, header -> header.get(JwsHeader.TYPE)).toString().equals(expectedTyp);
    }

    private boolean hasSubject(String token) {
        return !extractBodyClaims(token, Claims::getSubject).isBlank();
    }

    private boolean hasIssuer(String token) {
        String expectedIssuer = "http://localhost:8080/api/v1";
        return extractBodyClaims(token, Claims::getIssuer).equals(expectedIssuer);
    }

    private boolean hasIssuedDate(String token) {
        return !extractBodyClaims(token, Claims::getIssuedAt).toString().isBlank();
    }

    private boolean hasUserID(String token) {
        return extractAllClaims(token).getBody().containsKey(TokenUtils.ID_CLAIM_NAME);
    }

    private boolean hasUserRole(String token) {
        return extractAllClaims(token).getBody().containsKey(TokenUtils.USER_ROLE);
    }

    private boolean isExpired(String token) {
        return extractBodyClaims(token, Claims::getExpiration).before(new Date());
    }

    private boolean isBeforeDate(String token) {
        return extractBodyClaims(token, Claims::getNotBefore).after(new Date());
    }

    private Jws<Claims> extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
    }
}
