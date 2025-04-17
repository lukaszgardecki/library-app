package com.example.libraryapp.core.token;

import com.example.libraryapp.domain.token.ports.AccessTokenRepositoryPort;
import com.example.libraryapp.domain.token.ports.RefreshTokenRepositoryPort;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ValidateTokenAndFingerprintUseCase {
    private final AccessTokenRepositoryPort accessTokenRepository;
    private final RefreshTokenRepositoryPort refreshTokenRepository;
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
