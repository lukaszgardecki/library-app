package com.example.libraryapp.NEWapplication.auth;

import com.example.libraryapp.NEWapplication.token.TokenFacade;
import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.NEWdomain.token.dto.AuthTokensDto;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
import com.example.libraryapp.NEWdomain.user.exceptions.UserNotFoundException;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserAuthFailedEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserAuthSuccessEvent;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
class AuthenticateUserUseCase {
    private final UserFacade userFacade;
    private final TokenFacade tokenFacade;
    private final AuthenticationManagerPort authenticationManager;
    private final EventPublisherPort publisher;

    AuthTokensDto execute(String username, String password) {
        UserDto user = null;
        try {
            user = userFacade.getUserByEmail(username);
            authenticationManager.authenticate(user.getEmail(), password);
            publisher.publish(new UserAuthSuccessEvent(user.getId(), "Pomyślne logowanie użytkownika"));
            return tokenFacade.generateTokensFor(user);
        } catch (UserNotFoundException | AuthenticationException ex) {
            if (user != null) publisher.publish(new UserAuthFailedEvent(user.getId(), "Nieudane logowanie użytkownika"));
            throw new BadCredentialsException("Message.VALIDATION_BAD_CREDENTIALS.getMessage()");
        }
    }
}
