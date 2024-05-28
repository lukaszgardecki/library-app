package com.example.libraryapp.domain.token;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenGenerator {
    private final Key signingKey;
    @Value("${jwt.expirationTime}")
    private long tokenExpirationTime;
    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;

    String generateAccessToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, tokenExpirationTime);
    }

    String generateRefreshToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setHeaderParam(JwsHeader.TYPE, "JWT")
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setNotBefore(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuer("http://localhost:8080/api/v1")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
