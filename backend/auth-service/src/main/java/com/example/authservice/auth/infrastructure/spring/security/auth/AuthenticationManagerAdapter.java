package com.example.authservice.auth.infrastructure.spring.security.auth;

import com.example.authservice.auth.domain.model.Email;
import com.example.authservice.auth.domain.model.Password;
import com.example.authservice.auth.domain.ports.AuthenticationManagerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationManagerAdapter implements AuthenticationManagerPort {
    private final AuthenticationManager authenticationManager;

    @Override
    public boolean authenticate(Email username, Password password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username.value(), password.value())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication.isAuthenticated();
    }

    @Override
    public Email getCurrentUsername() {
        return new Email(SecurityContextHolder.getContext().getAuthentication().getName());
    }


}
