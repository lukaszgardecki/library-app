package com.example.libraryapp.domain.token;

import com.example.libraryapp.domain.config.AuthTokens;
import com.example.libraryapp.domain.config.Fingerprint;
import com.example.libraryapp.domain.config.SecurityUtils;
import com.example.libraryapp.domain.member.Member;
import com.example.libraryapp.management.Message;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenValidator validator;
    private final TokenGenerator generator;

    public void saveTokens(Member member, String accessToken, String refreshToken) {
        accessTokenRepository.save(new AccessToken(member, accessToken));
        refreshTokenRepository.save(new RefreshToken(member, refreshToken));
    }

    public void revokeAllUserTokens(Long userId) {
        List<AccessToken> validUserAccessTokens = accessTokenRepository.findAllValidTokenByUser(userId);
        List<RefreshToken> validUserRefreshTokens = refreshTokenRepository.findAllValidTokenByUser(userId);

        if (validUserAccessTokens.isEmpty() && validUserRefreshTokens.isEmpty()) return;

        validUserAccessTokens.forEach(Token::setInvalid);
        validUserRefreshTokens.forEach(Token::setInvalid);
        accessTokenRepository.saveAll(validUserAccessTokens);
        refreshTokenRepository.saveAll(validUserRefreshTokens);
    }

    public boolean isAccessTokenValidInDB(String token) {
        if (accessTokenRepository.existsValidToken(token)) return true;
        else throw new JwtException(Message.ACCESS_DENIED);
    }

    public boolean isRefreshTokenValidInDB(String token) {
        if (refreshTokenRepository.existsValidToken(token)) return true;
        else throw new JwtException(Message.ACCESS_DENIED);
    }
    public AuthTokens generateAuth(Member userDetails) {
        Fingerprint fingerprint = new Fingerprint();
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put(SecurityUtils.FINGERPRINT_NAME, fingerprint.getHash());
        extraClaims.put(SecurityUtils.ID_CLAIM_NAME, userDetails.getId());
        String accessToken = generator.generateAccessToken(extraClaims, userDetails);
        String refreshToken = generator.generateRefreshToken(extraClaims, userDetails);
        return new AuthTokens(accessToken, refreshToken, fingerprint);
    }

    public boolean validateTokenAndFgp(String token, String fingerprint) {
        boolean fgpIsValid = validator.validateFgp(token, fingerprint);
        boolean tokenIsValid = validator.validateToken(token);
        if (fgpIsValid && tokenIsValid) return true;
        else throw new JwtException(Message.ACCESS_DENIED);
    }

    public String extractUsername(String token) {
        return validator.extractBodyClaims(token, Claims::getSubject);
    }

    public Optional<String> findToken(HttpServletRequest request) {
        String BEARER_PREFIX = TokenType.BEARER.getPrefix();
        Optional<String> token = Optional.of(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER_PREFIX))
                .map(authHeader -> authHeader.substring(BEARER_PREFIX.length()));
        if (token.isEmpty()) throw new JwtException(Message.ACCESS_DENIED);
        else return token;
    }

    public Optional<String> findFingerprint(HttpServletRequest request) {
        Optional<String> fingerprint = Arrays.stream(request.getCookies())
                .filter(cookie -> SecurityUtils.FINGERPRINT_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue);
        if (fingerprint.isEmpty()) throw new JwtException(Message.ACCESS_DENIED);
        else return fingerprint;
    }
}
