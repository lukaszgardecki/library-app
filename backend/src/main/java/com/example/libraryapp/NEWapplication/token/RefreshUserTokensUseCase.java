package com.example.libraryapp.NEWapplication.token;

import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.token.dto.AuthTokensDto;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
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
