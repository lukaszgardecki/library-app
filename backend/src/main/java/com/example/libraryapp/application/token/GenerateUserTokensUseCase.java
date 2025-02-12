package com.example.libraryapp.application.token;

import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GenerateUserTokensUseCase {
    private final TokenService tokenService;

    AuthTokensDto execute(UserDto user) {
        return tokenService.generateNewTokensFor(user);
    }
}
