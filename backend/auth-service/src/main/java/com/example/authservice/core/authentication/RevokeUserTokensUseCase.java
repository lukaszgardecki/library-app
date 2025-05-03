package com.example.authservice.core.authentication;

import com.example.authservice.domain.model.authdetails.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RevokeUserTokensUseCase {
    private final TokenService tokenService;

    void execute(UserId userId) {
        tokenService.revokeUserTokens(userId);
    }
}
