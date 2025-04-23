package com.example.authservice.core.token;

import com.example.authservice.domain.dto.auth.UserAuthDto;
import com.example.authservice.domain.dto.token.TokenAuth;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GenerateUserTokensUseCase {
    private final TokenService tokenService;

    TokenAuth execute(UserAuthDto userAuth) {
        return tokenService.generateNewTokensFor(userAuth);
    }
}
