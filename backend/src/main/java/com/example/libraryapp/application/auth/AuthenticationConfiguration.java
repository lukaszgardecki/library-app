package com.example.libraryapp.application.auth;

import com.example.libraryapp.application.person.PersonConfiguration;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.token.TokenConfiguration;
import com.example.libraryapp.application.token.TokenFacade;
import com.example.libraryapp.application.user.UserConfiguration;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.domain.user.ports.UserRepository;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryAuthenticationManagerPortAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryUserRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AuthenticationConfiguration {

    public AuthenticationFacade authenticationFacade() {
        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
        AuthenticationManagerPort authenticationManager = new InMemoryAuthenticationManagerPortAdapter(userRepository);
        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
        TokenFacade tokenFacade = new TokenConfiguration().tokenFacade();
        PersonFacade personFacade = new PersonConfiguration().personFacade();
        return new AuthenticationFacade(
                new AuthenticateUserUseCase(userFacade, personFacade, tokenFacade, authenticationManager, null),
                new GetCurrentLoggedInUserUseCase(userFacade,authenticationManager)
        );
    }

    public AuthenticationFacade authenticationFacade(UserRepository userRepository) {
        AuthenticationManagerPort authenticationManager = new InMemoryAuthenticationManagerPortAdapter(userRepository);
        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
        TokenFacade tokenFacade = new TokenConfiguration().tokenFacade();
        PersonFacade personFacade = new PersonConfiguration().personFacade();
        return new AuthenticationFacade(
                new AuthenticateUserUseCase(userFacade, personFacade, tokenFacade, authenticationManager, null),
                new GetCurrentLoggedInUserUseCase(userFacade,authenticationManager)
        );
    }

    @Bean
    @Lazy
    AuthenticationFacade authenticationFacade(
            @Lazy UserFacade userFacade,
            @Lazy TokenFacade tokenFacade,
            @Lazy PersonFacade personFacade,
            @Lazy AuthenticationManagerPort authenticationManager,
            @Lazy EventPublisherPort publisher
    ) {
        return new AuthenticationFacade(
                new AuthenticateUserUseCase(userFacade, personFacade, tokenFacade, authenticationManager, publisher),
                new GetCurrentLoggedInUserUseCase(userFacade, authenticationManager)
        );
    }
}
