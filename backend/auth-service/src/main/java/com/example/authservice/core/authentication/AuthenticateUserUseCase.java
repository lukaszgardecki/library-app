package com.example.authservice.core.authentication;

import com.example.authservice.domain.i18n.MessageKey;
import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.token.Auth;
import com.example.authservice.domain.ports.out.AuthenticationManagerPort;
import com.example.authservice.domain.ports.out.EventPublisherPort;
import com.example.authservice.domain.ports.out.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

@RequiredArgsConstructor
class AuthenticateUserUseCase {
    private final AuthenticationManagerPort authenticationManager;
    private final AuthenticationService authenticationService;
    private final MessageProviderPort msgProvider;
    private final EventPublisherPort publisher;
    private final TokenService tokenService;

    Auth execute(Email username, Password password) {
        AuthDetails authDetails = null;
        try {
            authDetails = authenticationService.getAuthDetailsByEmail(username);
            authenticationManager.authenticate(authDetails.getEmail(), password);
            publisher.publishLoginSuccessEvent(authDetails.getUserId());
            return tokenService.generateNewAuth(authDetails.getUserId());
        } catch (Exception  ex) {
            if (authDetails != null) {
                publisher.publishLoginFailureEvent(authDetails.getUserId());
            }
            throw new BadCredentialsException(msgProvider.getMessage(MessageKey.VALIDATION_BAD_CREDENTIALS));
        }
    }
}
