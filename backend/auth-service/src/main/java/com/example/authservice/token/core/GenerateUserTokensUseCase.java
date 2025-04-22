package com.example.authservice.token.core;

import com.example.authservice.auth.domain.dto.UserAuthDto;
import com.example.authservice.token.domain.dto.TokenAuth;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GenerateUserTokensUseCase {
    private final TokenService tokenService;

    TokenAuth execute(UserAuthDto userAuth) {
        return tokenService.generateNewTokensFor(userAuth);
    }
}
