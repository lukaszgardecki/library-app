package com.example.libraryapp.application.token;

import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenFacade {
    private final GenerateUserTokensUseCase generateUserTokensUseCase;
    private final RefreshUserTokensUseCase refreshUserTokensUseCase;
    private final RevokeUserTokensUseCase revokeUserTokensUseCase;
    private final ValidateTokenAndFingerprintUseCase validateTokenAndFingerprintUseCase;
    private final GetUsernameFromTokenUseCase getUsernameFromTokenUseCase;

    public AuthTokensDto generateTokensFor(UserDto user) {
        return generateUserTokensUseCase.execute(user);
    }

    public AuthTokensDto refreshUserTokens(String token) {
        return refreshUserTokensUseCase.execute(token);
    }

    public void revokeUserTokens(Long userId) {
        revokeUserTokensUseCase.execute(userId);
    }

    public void validateTokenAndFingerprint(String token, String fingerprint, boolean isRefreshToken) {
        validateTokenAndFingerprintUseCase.execute(token, fingerprint, isRefreshToken);
    }

    public String getUsernameFrom(String token) {
        return getUsernameFromTokenUseCase.execute(token);
    }
}
