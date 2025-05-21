package com.example.authservice.core.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
class TokenValidator {
    private final TokenParser tokenParser;

    boolean validate(String token) {
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

    private boolean hasAlg(String token) {
        String expectedAlg = SignatureAlgorithm.HS256.getValue();
        return tokenParser.extractHeader(token, header -> header.get(JwsHeader.ALGORITHM)).toString().equals(expectedAlg);
    }

    private boolean hasTypJWT(String token) {
        String expectedTyp = "JWT";
        return tokenParser.extractHeader(token, header -> header.get(JwsHeader.TYPE)).toString().equals(expectedTyp);
    }

    private boolean hasSubject(String token) {
        return !tokenParser.extractBodyClaims(token, Claims::getSubject).isBlank();
    }

    private boolean hasIssuer(String token) {
        String expectedIssuer = "http://localhost:8080/api/v1";
        return tokenParser.extractBodyClaims(token, Claims::getIssuer).equals(expectedIssuer);
    }

    private boolean hasIssuedDate(String token) {
        return !tokenParser.extractBodyClaims(token, Claims::getIssuedAt).toString().isBlank();
    }

    private boolean hasUserID(String token) {
        return tokenParser.extractBodyClaims(token, claims -> claims.containsKey(TokenUtils.ID_CLAIM_NAME));
    }

    private boolean hasUserRole(String token) {
        return tokenParser.extractBodyClaims(token, claims -> claims.containsKey(TokenUtils.USER_ROLE));
    }

    private boolean isExpired(String token) {
        return tokenParser.extractBodyClaims(token, Claims::getExpiration).before(new Date());
    }

    private boolean isBeforeDate(String token) {
        return tokenParser.extractBodyClaims(token, Claims::getNotBefore).after(new Date());
    }
}
