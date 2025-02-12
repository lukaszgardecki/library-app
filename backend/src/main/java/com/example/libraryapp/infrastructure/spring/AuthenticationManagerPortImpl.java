package com.example.libraryapp.infrastructure.spring;

import com.example.libraryapp.domain.auth.ports.AuthenticationManagerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationManagerPortImpl implements AuthenticationManagerPort {
    private final AuthenticationManager authenticationManager;

    @Override
    public boolean authenticate(String username, String password) throws AuthenticationException {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        ).isAuthenticated();
    }

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
