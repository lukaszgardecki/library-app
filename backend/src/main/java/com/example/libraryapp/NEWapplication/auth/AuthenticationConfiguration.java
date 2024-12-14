package com.example.libraryapp.NEWapplication.auth;

import com.example.libraryapp.NEWapplication.token.TokenConfiguration;
import com.example.libraryapp.NEWapplication.token.TokenFacade;
import com.example.libraryapp.NEWapplication.user.UserConfiguration;
import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.auth.ports.AuthenticationManagerPort;
import com.example.libraryapp.NEWdomain.user.ports.UserRepository;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import com.example.libraryapp.NEWinfrastructure.persistence.inmemory.InMemoryAuthenticationManagerPortImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfiguration {

    public AuthenticationFacade authenticationFacade(UserRepository userRepository) {
        AuthenticationManagerPort authenticationManager = new InMemoryAuthenticationManagerPortImpl(userRepository);
        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
        TokenFacade tokenFacade = new TokenConfiguration().tokenFacade();
        return new AuthenticationFacade(
                new AuthenticateUserUseCase(userFacade, tokenFacade, authenticationManager, null),
                new GetCurrentLoggedInUserUseCase(userFacade,authenticationManager)
        );
    }

    @Bean
    AuthenticationFacade authenticationFacade(
            UserFacade userFacade,
            TokenFacade tokenFacade,
            AuthenticationManagerPort authenticationManager,
            EventPublisherPort publisher
    ) {
        return new AuthenticationFacade(
                new AuthenticateUserUseCase(userFacade, tokenFacade, authenticationManager, publisher),
                new GetCurrentLoggedInUserUseCase(userFacade, authenticationManager)
        );
    }
}
