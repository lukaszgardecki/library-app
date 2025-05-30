package com.example.authservice.core.authdetails;

import com.example.authservice.domain.ports.out.AuthDetailsRepositoryPort;
import com.example.authservice.domain.ports.out.PasswordEncoderPort;
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
                new CreateNewAuthDetailsUseCase(authDetailsService),
                new GetAuthDetailsUseCase(authDetailsService),
                new UpdateAuthDetailsUseCase(authDetailsService),
                new ValidateEmailUseCase(authDetailsService)
        );
    }
}
