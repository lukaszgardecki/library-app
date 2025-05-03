package com.example.authservice.core.authentication;

import com.example.authservice.domain.dto.token.AuthDto;
import com.example.authservice.domain.dto.token.TokenInfoDto;
import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.model.authdetails.Password;
import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.model.token.Auth;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationFacade {
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RefreshUserTokensUseCase refreshUserTokensUseCase;
    private final ValidateTokenAndCookieUseCase validateTokenAndCookieUseCase;
    private final ExtractUsernameFromTokenUseCase extractUsernameFromTokenUseCase;
    private final RevokeUserTokensUseCase revokeUserTokensUseCase;

    public AuthDto authenticate(Email username, Password password) {
        Auth auth = authenticateUserUseCase.execute(username, password);
        return AuthMapper.toDto(auth);
    }

    public AuthDto refreshUserTokens(TokenInfoDto tokenInfo) {
        Auth auth = refreshUserTokensUseCase.execute(tokenInfo);
        return AuthMapper.toDto(auth);
    }

    public UserId validateTokenAndCookie(TokenInfoDto tokenInfo, @Nullable String cookie) {
        return validateTokenAndCookieUseCase.execute(tokenInfo, cookie);
    }

    public String extractUsernameFromToken(String authHeader) {
        return extractUsernameFromTokenUseCase.execute(authHeader);
    }

    public void revokeTokensByUserId(UserId userId) {
        revokeUserTokensUseCase.execute(userId);
    }
}
