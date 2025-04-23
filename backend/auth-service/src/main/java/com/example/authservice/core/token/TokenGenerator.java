package com.example.authservice.core.token;

import com.example.authservice.domain.dto.auth.UserAuthDto;
import com.example.authservice.domain.dto.token.TokenAuth;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
class TokenGenerator {
    private final Key signingKey;
    @Value("${jwt.expirationTime}")
    private long tokenExpirationTime;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;

    TokenAuth generateTokenAuth(UserAuthDto userAuth) {
        Fingerprint fingerprint = FingerprintGenerator.generate();
        Map<String, Object> claims = new TokenClaimsBuilder()
                .addFingerprint(fingerprint)
                .addUserDetails(userAuth)
                .build();

        String accessToken = generateAccessToken(claims, userAuth.username());
        String refreshToken = generateRefreshToken(claims, userAuth.username());

        return new TokenAuth(accessToken, refreshToken, fingerprint.cookie());
    }
    private String generateAccessToken(
            Map<String, Object> extraClaims,
            String username
    ) {
        return buildToken(extraClaims, username, tokenExpirationTime);
    }

    private String generateRefreshToken(
            Map<String, Object> extraClaims,
            String username
    ) {
        return buildToken(extraClaims, username, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            String username,
            long expiration
    ) {
        Date now = new Date(System.currentTimeMillis());
        return Jwts
                .builder()
                .setHeaderParam(JwsHeader.TYPE, "JWT")
                .setClaims(extraClaims)
                .setSubject(username)
                .setNotBefore(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .setIssuer("http://localhost:8080/api/v1")
                .setIssuedAt(now)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
