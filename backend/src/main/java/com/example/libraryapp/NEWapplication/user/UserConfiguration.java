package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.auth.ports.PasswordEncoderPort;
import com.example.libraryapp.NEWdomain.user.ports.LibraryCardRepository;
import com.example.libraryapp.NEWdomain.user.ports.PersonRepository;
import com.example.libraryapp.NEWdomain.user.ports.UserRepository;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import com.example.libraryapp.NEWinfrastructure.persistence.inmemory.InMemoryLibraryCardRepositoryImpl;
import com.example.libraryapp.NEWinfrastructure.persistence.inmemory.InMemoryPasswordEncoderImpl;
import com.example.libraryapp.NEWinfrastructure.persistence.inmemory.InMemoryPersonRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    public UserFacade userFacade(UserRepository userRepository) {
        InMemoryPersonRepositoryImpl personRepository = new InMemoryPersonRepositoryImpl();
        LibraryCardRepository libraryCardRepository = new InMemoryLibraryCardRepositoryImpl();
        LibraryCardServiceService cardService = new LibraryCardServiceService(libraryCardRepository);
        InMemoryPasswordEncoderImpl passwordEncoder = new InMemoryPasswordEncoderImpl();
        return new UserFacade(
                new RegisterUserUseCase(userRepository, cardService, personRepository, passwordEncoder, null),
                new GetUserUseCase(userRepository),
                new UpdateUserAfterBookItemRequestUseCase(userRepository)
        );
    }

    @Bean
    UserFacade userFacade(
            UserRepository userRepository,
            LibraryCardRepository libraryCardRepository,
            PersonRepository personRepository,
            PasswordEncoderPort passwordEncoder,
            EventPublisherPort publisher
    ) {
        LibraryCardServiceService cardService = new LibraryCardServiceService(libraryCardRepository);
        return new UserFacade(
                new RegisterUserUseCase(userRepository, cardService, personRepository, passwordEncoder, publisher),
                new GetUserUseCase(userRepository),
                new UpdateUserAfterBookItemRequestUseCase(userRepository)
        );
    }

    @Bean
    UserEventListenerImpl userEventListener() {
        return new UserEventListenerImpl();
    }
}
