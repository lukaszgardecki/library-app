package com.example.libraryapp.core.auth;

import com.example.libraryapp.core.person.PersonConfiguration;
import com.example.libraryapp.core.person.PersonFacade;
import com.example.libraryapp.core.token.TokenConfiguration;
import com.example.libraryapp.core.token.TokenFacade;
import com.example.libraryapp.core.user.UserConfiguration;
import com.example.libraryapp.core.user.UserFacade;
import com.example.libraryapp.domain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.domain.user.ports.UserRepositoryPort;
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

    public AuthenticationFacade authenticationFacade(UserRepositoryPort userRepository) {
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
