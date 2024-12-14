package com.example.libraryapp.NEWapplication.token;

import com.example.libraryapp.NEWdomain.token.dto.AuthTokensDto;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GenerateUserTokensUseCase {
    private final TokenService tokenService;

    AuthTokensDto execute(UserDto user) {
        return tokenService.generateNewTokensFor(user);
    }
}
