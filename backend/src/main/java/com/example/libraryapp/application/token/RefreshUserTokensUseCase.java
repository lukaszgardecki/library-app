package com.example.libraryapp.application.token;

import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RefreshUserTokensUseCase {
    private final TokenService tokenService;
    private final UserFacade userFacade;
    private final TokenValidator validator;

    AuthTokensDto execute(String token) {
        String userEmail = validator.extractBodyClaims(token, Claims::getSubject);
        UserDto user = userFacade.getUserByEmail(userEmail);
        return tokenService.generateNewTokensFor(user);
    }
}
