package com.example.libraryapp.application.token;

import com.example.libraryapp.domain.token.model.Token;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetTokenByHashUseCase {
    private final TokenService tokenService;

    Token execute(String hash) {
        return tokenService.getTokenByHash(hash);
    }
}
