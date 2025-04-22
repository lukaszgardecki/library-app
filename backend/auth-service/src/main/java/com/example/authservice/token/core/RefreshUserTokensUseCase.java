package com.example.authservice.token.core;

import com.example.authservice.auth.core.AuthenticationFacade;
import com.example.authservice.auth.domain.dto.UserAuthDto;
import com.example.authservice.auth.domain.model.Email;
import com.example.authservice.token.domain.dto.TokenAuth;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RefreshUserTokensUseCase {
    private final TokenService tokenService;
    private final AuthenticationFacade authenticationFacade;
    private final TokenValidator validator;

    TokenAuth execute(String token) {
        Email userEmail = new Email(validator.extractBodyClaims(token, Claims::getSubject));
        UserAuthDto userAuth = authenticationFacade.getUserAuthByEmail(userEmail);
        return tokenService.generateNewTokensFor(userAuth);
    }
}
