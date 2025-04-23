package com.example.authservice.core.token;

import com.example.authservice.core.auth.AuthenticationFacade;
import com.example.authservice.domain.dto.auth.UserAuthDto;
import com.example.authservice.domain.model.auth.Email;
import com.example.authservice.domain.dto.token.TokenAuth;
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
