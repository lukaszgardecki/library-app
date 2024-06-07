package com.example.libraryapp.domain.config;

import com.example.libraryapp.domain.action.ActionRepository;
import com.example.libraryapp.domain.action.types.LogoutAction;
import com.example.libraryapp.domain.token.AccessToken;
import com.example.libraryapp.domain.token.AccessTokenRepository;
import com.example.libraryapp.domain.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final AccessTokenRepository accessTokenRepository;
    private final ActionRepository actionRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(TokenType.BEARER.getPrefix())) {
            return;
        }

        String jwt = authHeader.substring(7);
        AccessToken storedToken = accessTokenRepository.findByToken(jwt).orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            accessTokenRepository.save(storedToken);
            actionRepository.save(new LogoutAction(storedToken.getMember()));
            SecurityContextHolder.clearContext();
        }
    }
}
