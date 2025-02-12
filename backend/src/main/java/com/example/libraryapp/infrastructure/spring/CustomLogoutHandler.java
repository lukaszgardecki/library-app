package com.example.libraryapp.infrastructure.spring;

import com.example.libraryapp.application.token.TokenFacade;
import com.example.libraryapp.application.user.UserFacade;
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
//    private final ActionRepository actionRepository;

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
//        TODO: 05.12.2024 emituj event
////      actionRepository.save(new ActionLogout(MemberDtoMapper.map(storedToken.getMember())));

    }
}
