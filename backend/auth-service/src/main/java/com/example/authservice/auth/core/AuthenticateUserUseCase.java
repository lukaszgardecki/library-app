package com.example.authservice.auth.core;

import com.example.authservice.auth.domain.exceptions.UserAuthNotFoundException;
import com.example.authservice.auth.domain.model.Email;
import com.example.authservice.auth.domain.model.Password;
import com.example.authservice.auth.domain.model.UserAuth;
import com.example.authservice.auth.domain.ports.AuthenticationManagerPort;
import com.example.authservice.auth.domain.ports.EventPublisherPort;
import com.example.authservice.token.core.TokenFacade;
import com.example.authservice.token.domain.dto.TokenAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
class AuthenticateUserUseCase {
    private final AuthenticationService authenticationService;
    private final TokenFacade tokenFacade;
    private final AuthenticationManagerPort authenticationManager;
    private final EventPublisherPort publisher;

    TokenAuth execute(Email username, Password password) {
        UserAuth userAuth = null;
        try {
            userAuth = authenticationService.getUserAuthByEmail(username);
            authenticationManager.authenticate(userAuth.getEmail(), password);
            publisher.publishLoginSuccessEvent(userAuth.getUserId());
            return tokenFacade.generateTokensFor(AuthMapper.toDto(userAuth));
        } catch (UserAuthNotFoundException | AuthenticationException ex) {
            if (userAuth != null) {
                publisher.publishLoginFailureEvent(userAuth.getUserId());
            }
            throw new BadCredentialsException("");
        }
    }
}
