package com.example.authservice.core.authentication;

import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.model.authdetails.Role;
import com.example.authservice.domain.model.token.CookieValues;
import com.example.authservice.domain.model.token.Token;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
class TokenGenerator {
    private final Key signingKey;
    @Value("${jwt.expirationTime}")
    private long accessTokenExpTime;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpTime;

    Token generateAccessToken(AuthDetailsDto authDetails, CookieValues cookieValues) {
        return generate(authDetails, cookieValues, accessTokenExpTime);
    }

    Token generateRefreshToken(AuthDetailsDto authDetails, CookieValues cookieValues) {
        return generate(authDetails, cookieValues, refreshTokenExpTime);
    }

    private Token generate(AuthDetailsDto authDetails, CookieValues cookieValues, long expTime) {
        Map<String, Object> claims = new TokenClaimsBuilder()
                .addFingerprint(cookieValues.hash())
                .addUserDetails(authDetails.userId(), authDetails.role())
                .build();
        String token = buildToken(claims, authDetails.username(), expTime);
        return new Token(authDetails.userId(), token);
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
