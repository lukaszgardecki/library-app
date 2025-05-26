package com.example.authservice.core.registration;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.ports.out.EventPublisherPort;
import com.example.authservice.domain.ports.out.UserServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistrationConfiguration {

    @Bean
    public RegistrationFacade registrationFacade(
            AuthDetailsFacade authDetailsFacade,
            UserServicePort userService,
            EventPublisherPort publisher
    ) {
        RegistrationService registrationService = new RegistrationService(authDetailsFacade);
        return new RegistrationFacade(
                new RegisterUserUseCase(registrationService, userService, publisher)
        );
    }
}
