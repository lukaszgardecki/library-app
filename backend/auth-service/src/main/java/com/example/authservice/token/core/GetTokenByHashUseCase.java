package com.example.authservice.token.core;

import com.example.authservice.token.domain.model.Token;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetTokenByHashUseCase {
    private final TokenService tokenService;

    Token execute(String hash) {
        return tokenService.getTokenByHash(hash);
    }
}
