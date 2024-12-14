package com.example.libraryapp.NEWapplication.token;

import com.example.libraryapp.NEWdomain.token.dto.AuthTokensDto;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
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

//    AuthTokensDto generateAuth(UserDto user) {
//        Fingerprint fingerprint = new Fingerprint();
//        Map<String, Object> extraClaims = new HashMap<>();
//        extraClaims.put(TokenUtils.FINGERPRINT_NAME, fingerprint.getHash());
//        extraClaims.put(TokenUtils.ID_CLAIM_NAME, user.getId());
//        extraClaims.put(TokenUtils.USER_ROLE, user.getRole().name());
//        String accessToken = generateAccessToken(extraClaims, user.getEmail());
//        String refreshToken = generateRefreshToken(extraClaims, user.getEmail());
//        return new AuthTokensDto(accessToken, refreshToken, fingerprint.getCookie());
//    }

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
        return Jwts
                .builder()
                .setHeaderParam(JwsHeader.TYPE, "JWT")
                .setClaims(extraClaims)
                .setSubject(username)
                .setNotBefore(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuer("http://localhost:8080/api/v1")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
