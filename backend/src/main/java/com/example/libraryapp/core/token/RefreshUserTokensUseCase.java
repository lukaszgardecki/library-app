package com.example.libraryapp.core.token;

import com.example.libraryapp.core.user.UserFacade;
import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.model.Email;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RefreshUserTokensUseCase {
    private final TokenService tokenService;
    private final UserFacade userFacade;
    private final TokenValidator validator;

    AuthTokensDto execute(String token) {
        Email userEmail = new Email(validator.extractBodyClaims(token, Claims::getSubject));
        UserDto user = userFacade.getUserByEmail(userEmail);
        return tokenService.generateNewTokensFor(user);
    }
}
