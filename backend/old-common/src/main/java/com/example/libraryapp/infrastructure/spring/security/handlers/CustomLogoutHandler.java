package com.example.libraryapp.infrastructure.spring.security.handlers;

import com.example.libraryapp.core.person.PersonFacade;
import com.example.libraryapp.core.token.TokenFacade;
import com.example.libraryapp.core.user.UserFacade;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.event.types.user.UserLogoutEvent;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.person.model.PersonFirstName;
import com.example.libraryapp.domain.person.model.PersonId;
import com.example.libraryapp.domain.person.model.PersonLastName;
import com.example.libraryapp.domain.token.dto.TokenDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.model.Email;
import com.example.libraryapp.domain.user.model.UserId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final TokenFacade tokenFacade;
    private final UserFacade userFacade;
    private final PersonFacade personFacade;
    private final EventPublisherPort publisher;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String tokenHash = tokenFacade.extractTokenFromHeader(request);
        if (tokenHash.isEmpty()) return;
        TokenDto storedToken = tokenFacade.getTokenByHash(tokenHash);

        if (storedToken != null) {
            String username = tokenFacade.getUsernameFrom(storedToken.getToken());
            UserDto user = userFacade.getUserByEmail(new Email(username));
            tokenFacade.revokeUserTokens(user.getId());
            SecurityContextHolder.clearContext();
            PersonDto person = personFacade.getPersonById(new PersonId(user.getPersonId()));
            publisher.publish(
                    new UserLogoutEvent(
                            new UserId(user.getId()),
                            new PersonFirstName(person.getFirstName()),
                            new PersonLastName(person.getLastName())
                    )
            );
        }
    }
}
