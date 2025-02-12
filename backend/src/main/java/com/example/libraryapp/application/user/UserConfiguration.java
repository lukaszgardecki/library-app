package com.example.libraryapp.application.user;

import com.example.libraryapp.application.auth.AuthenticationConfiguration;
import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookConfiguration;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemConfiguration;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.bookitemloan.BookItemLoanConfiguration;
import com.example.libraryapp.application.bookitemloan.BookItemLoanFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestConfiguration;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.application.fine.FineConfiguration;
import com.example.libraryapp.application.fine.FineFacade;
import com.example.libraryapp.application.librarycard.LibraryCardConfiguration;
import com.example.libraryapp.application.librarycard.LibraryCardFacade;
import com.example.libraryapp.application.person.PersonConfiguration;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.statistics.StatisticsConfiguration;
import com.example.libraryapp.application.statistics.StatisticsFacade;
import com.example.libraryapp.domain.auth.ports.PasswordEncoderPort;
import com.example.libraryapp.domain.user.ports.UserRepository;
import com.example.libraryapp.infrastructure.events.publishers.EventPublisherPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryEventPublisherImpl;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryPasswordEncoderImpl;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryPersonRepositoryImpl;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryUserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class UserConfiguration {

    public UserFacade userFacade() {
        InMemoryUserRepositoryImpl userRepository = new InMemoryUserRepositoryImpl();
        return userFacade(userRepository);
    }

    public UserFacade userFacade(UserRepository userRepository) {
        InMemoryPersonRepositoryImpl personRepository = new InMemoryPersonRepositoryImpl();
        PersonFacade personFacade = new PersonConfiguration().personFacade(personRepository);
        AuthenticationFacade authFacade = new AuthenticationConfiguration().authenticationFacade(userRepository);
        BookFacade bookFacade = new BookConfiguration().bookFacade();
        BookItemFacade bookItemFacade = new BookItemConfiguration().bookItemFacade();
        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
        BookItemLoanFacade bookItemLoanFacade = new BookItemLoanConfiguration().bookItemLoanFacade();
        FineFacade fineFacade = new FineConfiguration().fineFacade();
        LibraryCardFacade libraryCardFacade = new LibraryCardConfiguration().LibraryCardFacade();
        StatisticsFacade statisticsFacade = new StatisticsConfiguration().statisticsFacade();
        InMemoryPasswordEncoderImpl passwordEncoder = new InMemoryPasswordEncoderImpl();
        InMemoryEventPublisherImpl publisher = new InMemoryEventPublisherImpl();
        UserCredentialsService credentialsService = new UserCredentialsService(userRepository, passwordEncoder);
        UserService userService = new UserService(
                userRepository, credentialsService, bookFacade, bookItemFacade, bookItemLoanFacade, bookItemRequestFacade,
                fineFacade, personFacade, libraryCardFacade, statisticsFacade
        );
        return new UserFacade(
                new RegisterUserUseCase(userRepository, personFacade, libraryCardFacade, credentialsService, publisher),
                new GetUserUseCase(userService),
                new GetUserAdminInfoUseCase(userService, authFacade),
                new GetUserListPreviewUseCase(userService),
                new UpdateUserUseCase(userService),
                new UpdateUserByAdminUseCase(userService),
                new DeleteUserUseCase(userService, authFacade, bookItemRequestFacade),
                new VerifyUserForBookItemRequestUseCase(userService),
                new VerifyUserForBookItemLoanUseCase(userService),
                new VerifyUserForBookItemRenewalUseCase(userService),
                new CountAllUseCase(userService),
                new CountNewRegisteredUsersByMonthUseCase(userService),
                new GetUsersByLoanCountDescendingUseCase(userService),
                new SearchUserPreviewsUseCase(userService)
        );
    }

    @Bean
    UserFacade userFacade(
            UserRepository userRepository,
            AuthenticationFacade authFacade,
            PersonFacade personFacade,
            BookFacade bookFacade,
            @Lazy BookItemFacade bookItemFacade,
            @Lazy BookItemRequestFacade bookItemRequestFacade,
            @Lazy BookItemLoanFacade bookItemLoanFacade,
            FineFacade fineFacade,
            LibraryCardFacade libraryCardFacade,
            @Lazy StatisticsFacade statisticsFacade,
            PasswordEncoderPort passwordEncoder,
            EventPublisherPort publisher
    ) {
        UserCredentialsService credentialsService = new UserCredentialsService(userRepository, passwordEncoder);
        UserService userService = new UserService(
                userRepository, credentialsService, bookFacade, bookItemFacade, bookItemLoanFacade, bookItemRequestFacade,
                fineFacade, personFacade, libraryCardFacade, statisticsFacade
        );
        return new UserFacade(
                new RegisterUserUseCase(userRepository, personFacade, libraryCardFacade, credentialsService, publisher),
                new GetUserUseCase(userService),
                new GetUserAdminInfoUseCase(userService, authFacade),
                new GetUserListPreviewUseCase(userService),
                new UpdateUserUseCase(userService),
                new UpdateUserByAdminUseCase(userService),
                new DeleteUserUseCase(userService, authFacade, bookItemRequestFacade),
                new VerifyUserForBookItemRequestUseCase(userService),
                new VerifyUserForBookItemLoanUseCase(userService),
                new VerifyUserForBookItemRenewalUseCase(userService),
                new CountAllUseCase(userService),
                new CountNewRegisteredUsersByMonthUseCase(userService),
                new GetUsersByLoanCountDescendingUseCase(userService),
                new SearchUserPreviewsUseCase(userService)
        );
    }

//    @Bean
//    UserEventListenerImpl userEventListener(
//            UserRepository userRepository) {
//        UserService userService = new UserService(userRepository, bookItemLoanFacade, fineFacade);
//        return new UserEventListenerImpl(userService);
//    }
}
