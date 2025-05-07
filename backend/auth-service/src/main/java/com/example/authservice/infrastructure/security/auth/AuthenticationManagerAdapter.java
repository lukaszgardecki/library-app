package com.example.authservice.infrastructure.security.auth;

import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.ports.out.AuthenticationManagerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AuthenticationManagerAdapter implements AuthenticationManagerPort {
    private final AuthenticationConfiguration configuration;

    @Override
    public boolean authenticate(Email username, Password password) throws Exception {
        Authentication authentication = configuration.getAuthenticationManager().authenticate(
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
