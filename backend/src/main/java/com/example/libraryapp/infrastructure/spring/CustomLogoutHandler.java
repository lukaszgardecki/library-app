package com.example.libraryapp.infrastructure.spring;

import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.token.TokenFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.event.types.user.UserLogoutEvent;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserDto user = userFacade.getUserByEmail(username);
        tokenFacade.revokeUserTokens(user.getId());
        SecurityContextHolder.clearContext();
        PersonDto person = personFacade.getPersonById(user.getPersonId());
        publisher.publish(new UserLogoutEvent(user.getId(), person.getFirstName(), person.getLastName()));
    }
}
