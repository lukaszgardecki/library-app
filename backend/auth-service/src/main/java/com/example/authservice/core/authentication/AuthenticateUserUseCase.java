package com.example.authservice.core.authentication;

import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.exception.UserAuthNotFoundException;
import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.model.authdetails.Password;
import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.model.token.Auth;
import com.example.authservice.domain.ports.AuthenticationManagerPort;
import com.example.authservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
class AuthenticateUserUseCase {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final AuthenticationManagerPort authenticationManager;
    private final EventPublisherPort publisher;

    Auth execute(Email username, Password password) {
        AuthDetailsDto authDetails = null;
        try {
            authDetails = authenticationService.getAuthDetailsByEmail(username);
            authenticationManager.authenticate(new Email(authDetails.username()), password);
            publisher.publishLoginSuccessEvent(new UserId(authDetails.userId()));
            return tokenService.generateNewAuth(new UserId(authDetails.userId()));
        } catch (UserAuthNotFoundException | AuthenticationException ex) {
            if (authDetails != null) {
                publisher.publishLoginFailureEvent(new UserId(authDetails.userId()));
            }
            throw new BadCredentialsException("");
        }
    }
}
