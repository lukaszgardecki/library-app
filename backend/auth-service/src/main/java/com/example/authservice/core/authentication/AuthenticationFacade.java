package com.example.authservice.core.authentication;

import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.model.token.Auth;
import com.example.authservice.domain.model.token.TokenInfo;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationFacade {
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RefreshUserTokensUseCase refreshUserTokensUseCase;
    private final ValidateTokenAndCookieUseCase validateTokenAndCookieUseCase;
    private final ExtractUsernameFromTokenUseCase extractUsernameFromTokenUseCase;
    private final RevokeUserTokensUseCase revokeUserTokensUseCase;

    public Auth authenticate(Email username, Password password) {
        return authenticateUserUseCase.execute(username, password);
    }

    public Auth refreshUserTokens(TokenInfo tokenInfo) {
        return refreshUserTokensUseCase.execute(tokenInfo);
    }

    public UserId validateToken(TokenInfo tokenInfo) {
        return validateTokenAndCookieUseCase.execute(tokenInfo);
    }

    public UserId validateTokenAndCookie(TokenInfo tokenInfo, @Nullable String cookie) {
        return validateTokenAndCookieUseCase.execute(tokenInfo, cookie);
    }

    public String extractUsernameFromToken(String authHeader) {
        return extractUsernameFromTokenUseCase.execute(authHeader);
    }

    public void revokeTokensByUserId(UserId userId) {
        revokeUserTokensUseCase.execute(userId);
    }
}
