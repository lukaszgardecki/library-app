package com.example.libraryapp.application.token;

import com.example.libraryapp.domain.token.ports.AccessTokenRepository;
import com.example.libraryapp.domain.token.ports.RefreshTokenRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ValidateTokenAndFingerprintUseCase {
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenValidator validator;

    void execute(String token, String fingerprint, boolean isRefreshToken) {
        if (token == null || fingerprint == null) throw new JwtException("Message.ACCESS_DENIED.getMessage()");
        boolean fgpIsValid = validator.validateFgp(token, fingerprint);
        boolean tokenIsValid = validator.validateToken(token);
        boolean tokenExistsInDb = isRefreshToken ?
                refreshTokenRepository.existsValidToken(token) :
                accessTokenRepository.existsValidToken(token);
        if (!fgpIsValid || !tokenIsValid || !tokenExistsInDb) throw new JwtException("Message.ACCESS_DENIED.getMessage()");
    }
}
