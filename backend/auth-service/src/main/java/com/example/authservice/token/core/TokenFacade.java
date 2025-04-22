package com.example.authservice.token.core;

import com.example.authservice.auth.domain.dto.UserAuthDto;
import com.example.authservice.token.domain.dto.TokenAuth;
import com.example.authservice.token.domain.dto.TokenDto;
import com.example.authservice.token.domain.model.Token;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenFacade {
    private final GenerateUserTokensUseCase generateUserTokensUseCase;
    private final RefreshUserTokensUseCase refreshUserTokensUseCase;
    private final RevokeUserTokensUseCase revokeUserTokensUseCase;
    private final ValidateTokenAndFingerprintUseCase validateTokenAndFingerprintUseCase;
    private final ExtractTokenFromHeaderUseCase extractTokenFromHeaderUseCase;
    private final ExtractFingerprintFromHeaderUseCase extractFingerprintFromHeaderUseCase;
    private final GetUsernameFromTokenUseCase getUsernameFromTokenUseCase;
    private final GetTokenByHashUseCase getTokenByHashUseCase;

    public TokenAuth generateTokensFor(UserAuthDto userAuth) {
        return generateUserTokensUseCase.execute(userAuth);
    }

    public TokenAuth refreshUserTokens(String token) {
        return refreshUserTokensUseCase.execute(token);
    }

    public void revokeUserTokens(Long userId) {
        revokeUserTokensUseCase.execute(userId);
    }

    public void validateTokenAndFingerprint(String token, String fingerprint, boolean isRefreshToken) {
        validateTokenAndFingerprintUseCase.execute(token, fingerprint, isRefreshToken);
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        return extractTokenFromHeaderUseCase.execute(request);
    }

    public String extractFingerprintFromHeader(HttpServletRequest request) {
        return extractFingerprintFromHeaderUseCase.execute(request);
    }

    public TokenDto getTokenByHash(String hash) {
        Token token = getTokenByHashUseCase.execute(hash);
        return TokenMapper.toDto(token);
    }

    public String getUsernameFrom(String token) {
        return getUsernameFromTokenUseCase.execute(token);
    }
}
