package com.example.authservice.auth.core;

import com.example.authservice.auth.domain.ports.AuthenticationManagerPort;
import com.example.authservice.auth.domain.ports.EventPublisherPort;
import com.example.authservice.auth.domain.ports.PasswordEncoderPort;
import com.example.authservice.auth.domain.ports.UserAuthRepositoryPort;
import com.example.authservice.token.core.TokenFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AuthenticationConfiguration {

//    public AuthenticationFacade authenticationFacade() {
//        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
//        AuthenticationManagerPort authenticationManager = new InMemoryAuthenticationManagerPortAdapter(userRepository);
//        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
//        TokenFacade tokenFacade = new TokenConfiguration().tokenFacade();
//        PersonFacade personFacade = new PersonConfiguration().personFacade();
//        return new AuthenticationFacade(
//                new AuthenticateUserUseCase(userFacade, personFacade, tokenFacade, authenticationManager, null),
//                new GetCurrentLoggedInUserUseCase(userFacade,authenticationManager)
//        );
//    }

//    public AuthenticationFacade authenticationFacade(UserRepositoryPort userRepository) {
//        AuthenticationManagerPort authenticationManager = new InMemoryAuthenticationManagerPortAdapter(userRepository);
//        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
//        TokenFacade tokenFacade = new TokenConfiguration().tokenFacade();
//        PersonFacade personFacade = new PersonConfiguration().personFacade();
//        return new AuthenticationFacade(
//                new AuthenticateUserUseCase(userFacade, personFacade, tokenFacade, authenticationManager, null),
//                new GetCurrentLoggedInUserUseCase(userFacade,authenticationManager)
//        );
//    }

    @Bean
    AuthenticationFacade authenticationFacade(
            UserAuthRepositoryPort userAuthRepository,
            @Lazy TokenFacade tokenFacade,
            @Lazy AuthenticationManagerPort authenticationManager,
            @Lazy EventPublisherPort publisher,
            PasswordEncoderPort passwordEncoder
    ) {
        AuthenticationService authenticationService = new AuthenticationService(userAuthRepository, passwordEncoder);

        return new AuthenticationFacade(
                new GetUserAuthUseCase(authenticationService),
                new SaveUserCredentialsUseCase(authenticationService),
                new UpdateUserAuthUseCase(authenticationService),
                new AuthenticateUserUseCase(authenticationService, tokenFacade, authenticationManager, publisher),
                new GetCurrentLoggedInUserUseCase(authenticationService, authenticationManager)
        );
    }
}
