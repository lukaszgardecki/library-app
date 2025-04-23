package com.example.authservice.core.token;

import com.example.authservice.domain.model.token.Token;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetTokenByHashUseCase {
    private final TokenService tokenService;

    Token execute(String hash) {
        return tokenService.getTokenByHash(hash);
    }
}
