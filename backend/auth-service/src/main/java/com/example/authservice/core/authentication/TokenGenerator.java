package com.example.authservice.core.authentication;

import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.values.Role;
import com.example.authservice.domain.model.token.CookieValues;
import com.example.authservice.domain.model.token.Token;
import com.example.authservice.domain.model.token.values.TokenType;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.authservice.domain.model.token.values.TokenType.ACCESS;
import static com.example.authservice.domain.model.token.values.TokenType.REFRESH;

@RequiredArgsConstructor
class TokenGenerator {
    private final Key signingKey;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;

    Token generateAccessToken(AuthDetails authDetails, CookieValues cookieValues) {
        return generate(authDetails, cookieValues, accessTokenExpTime, ACCESS);
    }

    Token generateRefreshToken(AuthDetails authDetails, CookieValues cookieValues) {
        return generate(authDetails, cookieValues, refreshTokenExpTime, REFRESH);
    }

    private Token generate(AuthDetails authDetails, CookieValues cookieValues, long expTime, TokenType tokenType) {
        Map<String, Object> claims = new TokenClaimsBuilder()
                .addFingerprint(cookieValues.hash())
                .addUserDetails(authDetails.getUserId().value(), authDetails.getRole())
                .build();
        String token = buildToken(claims, authDetails.getEmail().value(), expTime);
        return new Token(authDetails.getUserId().value(), token, tokenType);
    }

    private String buildToken(
            Map<String, Object> claims,
            String username,
            long expiration
    ) {
        Date now = new Date(System.currentTimeMillis());
        return Jwts
                .builder()
                .setHeaderParam(JwsHeader.TYPE, "JWT")
                .setClaims(claims)
                .setSubject(username)
                .setNotBefore(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .setIssuer("http://localhost:8080/api/v1")
                .setIssuedAt(now)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private static class TokenClaimsBuilder {
        private final Map<String, Object> claims = new HashMap<>();

        TokenClaimsBuilder addFingerprint(String cookieValHash) {
            claims.put(CookieValueGenerator.FINGERPRINT_NAME, cookieValHash);
            return this;
        }

        TokenClaimsBuilder addUserDetails(Long userId, Role role) {
            claims.put(TokenUtils.ID_CLAIM_NAME, userId);
            claims.put(TokenUtils.USER_ROLE, role.name());
            return this;
        }

        Map<String, Object> build() {
            return new HashMap<>(claims);
        }
    }
}
