package com.example.authservice.core.authentication;

import com.example.authservice.domain.dto.token.AuthDto;
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
    private final ExtractTokenFromHeaderUseCase extractTokenFromHeaderUseCase;

    public AuthDto authenticate(Email username, Password password) {
        Auth auth = authenticateUserUseCase.execute(username, password);
        return AuthMapper.toDto(auth);
    }

    public AuthDto refreshUserTokens(String token) {
        Auth auth = refreshUserTokensUseCase.execute(token);
        return AuthMapper.toDto(auth);
    }

    public UserId validateTokenAndCookie(
            @Nullable String token, @Nullable String cookie) {
        return validateTokenAndCookieUseCase.execute(token, cookie);
    }

    public String extractTokenFromHeader(String authHeader) {
        return extractTokenFromHeaderUseCase.execute(authHeader);
    }
}
