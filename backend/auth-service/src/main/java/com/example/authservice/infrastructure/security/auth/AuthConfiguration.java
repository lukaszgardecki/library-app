package com.example.authservice.infrastructure.security.auth;

import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.ports.AuthDetailsRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class AuthConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AuthDetailsRepositoryPort authUserRepository) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(authUserRepository));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(AuthDetailsRepositoryPort userAuthRepository) {
        return username -> userAuthRepository.findByEmail(new Email(username))
                .map(CustomAuthDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User auth with email %s not found".formatted(username)));
    }
}
