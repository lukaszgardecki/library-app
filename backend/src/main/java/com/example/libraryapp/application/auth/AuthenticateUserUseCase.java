package com.example.libraryapp.application.auth;

import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.token.TokenFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.person.model.PersonFirstName;
import com.example.libraryapp.domain.person.model.PersonId;
import com.example.libraryapp.domain.person.model.PersonLastName;
import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.exceptions.UserNotFoundException;
import com.example.libraryapp.domain.event.types.user.UserAuthFailedEvent;
import com.example.libraryapp.domain.event.types.user.UserAuthSuccessEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.user.model.Email;
import com.example.libraryapp.domain.user.model.Password;
import com.example.libraryapp.domain.user.model.UserId;
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

    AuthTokensDto execute(Email username, Password password) {
        UserDto user = null;
        PersonDto person = null;
        try {
            user = userFacade.getUserByEmail(username);
            person = personFacade.getPersonById(new PersonId(user.getPersonId()));
            authenticationManager.authenticate(new Email(user.getEmail()), password);
            publisher.publish(new UserAuthSuccessEvent(
                    new UserId(user.getId()),
                    new PersonFirstName(person.getFirstName()),
                    new PersonLastName(person.getLastName())
            ));
            return tokenFacade.generateTokensFor(user);
        } catch (UserNotFoundException | AuthenticationException ex) {
            if (user != null && person != null) {
                publisher.publish(new UserAuthFailedEvent(
                        new UserId(user.getId()),
                        new PersonFirstName(person.getFirstName()),
                        new PersonLastName(person.getLastName())
                ));
            }
            throw new BadCredentialsException("");
        }
    }
}
