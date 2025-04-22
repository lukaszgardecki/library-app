package com.example.authservice.auth.infrastructure.spring.security.handlers;

import com.example.authservice.auth.core.AuthenticationFacade;
import com.example.authservice.auth.domain.dto.UserAuthDto;
import com.example.authservice.auth.domain.model.Email;
import com.example.authservice.auth.domain.model.UserId;
import com.example.authservice.auth.domain.ports.EventPublisherPort;
import com.example.authservice.token.core.TokenFacade;
import com.example.authservice.token.domain.dto.TokenDto;
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
    private final AuthenticationFacade authenticationFacade;
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
            UserAuthDto userAuth = authenticationFacade.getUserAuthByEmail(new Email(username));
            tokenFacade.revokeUserTokens(userAuth.userId());
            SecurityContextHolder.clearContext();
            publisher.publishLogoutSuccessEvent(new UserId(userAuth.userId()));
        }
    }
}
