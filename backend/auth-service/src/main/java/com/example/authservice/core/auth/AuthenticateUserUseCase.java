package com.example.authservice.core.auth;

import com.example.authservice.domain.exception.UserAuthNotFoundException;
import com.example.authservice.domain.model.auth.Email;
import com.example.authservice.domain.model.auth.Password;
import com.example.authservice.domain.model.auth.UserAuth;
import com.example.authservice.domain.ports.AuthenticationManagerPort;
import com.example.authservice.domain.ports.EventPublisherPort;
import com.example.authservice.core.token.TokenFacade;
import com.example.authservice.domain.dto.token.TokenAuth;
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
