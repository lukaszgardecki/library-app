package com.example.libraryapp.application.token;

import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
class TokenGenerator {
    private final Key signingKey;
    @Value("${jwt.expirationTime}")
    private long tokenExpirationTime;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;

    AuthTokensDto generateAuth(UserDto user) {
        Fingerprint fingerprint = FingerprintGenerator.generate();
        Map<String, Object> claims = new TokenClaimsBuilder()
                .addFingerprint(fingerprint)
                .addUserDetails(user)
                .build();

        String accessToken = generateAccessToken(claims, user.getEmail());
        String refreshToken = generateRefreshToken(claims, user.getEmail());

        return new AuthTokensDto(accessToken, refreshToken, fingerprint.cookie());
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
