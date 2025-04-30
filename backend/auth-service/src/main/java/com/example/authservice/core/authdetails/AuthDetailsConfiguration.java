package com.example.authservice.core.authdetails;

import com.example.authservice.domain.ports.AuthDetailsRepositoryPort;
import com.example.authservice.domain.ports.PasswordEncoderPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthDetailsConfiguration {

    @Bean
    AuthDetailsFacade authDetailsFacade(
            AuthDetailsRepositoryPort authDetailsRepository,
            PasswordEncoderPort passwordEncoder
    ) {
        AuthDetailsService authDetailsService = new AuthDetailsService(authDetailsRepository, passwordEncoder);
        return new AuthDetailsFacade(
                new GetAuthDetailsUseCase(authDetailsService),
                new CreateAuthDetailsUseCase(authDetailsService),
                new UpdateAuthDetailsUseCase(authDetailsService)
        );
    }
}
