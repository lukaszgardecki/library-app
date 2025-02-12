package com.example.libraryapp.application.auth;

import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.token.TokenFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.exceptions.UserNotFoundException;
import com.example.libraryapp.domain.event.types.user.UserAuthFailedEvent;
import com.example.libraryapp.domain.event.types.user.UserAuthSuccessEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
class AuthenticateUserUseCase {
    private final UserFacade userFacade;
    private final PersonFacade personFacade;
    private final TokenFacade tokenFacade;
    private final AuthenticationManagerPort authenticationManager;
    private final EventPublisherPort publisher;

    AuthTokensDto execute(String username, String password) {
        UserDto user = null;
        PersonDto person = null;
        try {
            user = userFacade.getUserByEmail(username);
            person = personFacade.getPersonById(user.getPersonId());
            authenticationManager.authenticate(user.getEmail(), password);
            publisher.publish(new UserAuthSuccessEvent(user.getId(), person.getFirstName(), person.getLastName(),"Pomyślne logowanie użytkownika"));
            return tokenFacade.generateTokensFor(user);
        } catch (UserNotFoundException | AuthenticationException ex) {
            if (user != null && person != null) {
                publisher.publish(new UserAuthFailedEvent(user.getId(), person.getFirstName(), person.getLastName(), "Nieudane logowanie użytkownika"));
            }
            throw new BadCredentialsException("Message.VALIDATION_BAD_CREDENTIALS.getMessage()");
        }
    }
}
