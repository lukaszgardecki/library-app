package com.example.libraryapp.application.token;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RevokeUserTokensUseCase {
    private final TokenService tokenService;

    void execute(Long userId) {
        tokenService.revokeUserTokens(userId);
    }
}
