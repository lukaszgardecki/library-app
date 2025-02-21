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
import com.example.libraryapp.domain.user.ports.UserRepositoryPort;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryEventPublisherAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryPasswordEncoderAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryPersonRepositoryAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryUserRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class UserConfiguration {

    public UserFacade userFacade() {
        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
        return userFacade(userRepository);
    }

    public UserFacade userFacade(UserRepositoryPort userRepository) {
        InMemoryPersonRepositoryAdapter personRepository = new InMemoryPersonRepositoryAdapter();
        PersonFacade personFacade = new PersonConfiguration().personFacade(personRepository);
        AuthenticationFacade authFacade = new AuthenticationConfiguration().authenticationFacade(userRepository);
        BookFacade bookFacade = new BookConfiguration().bookFacade();
        BookItemFacade bookItemFacade = new BookItemConfiguration().bookItemFacade();
        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
        BookItemLoanFacade bookItemLoanFacade = new BookItemLoanConfiguration().bookItemLoanFacade();
        FineFacade fineFacade = new FineConfiguration().fineFacade();
        LibraryCardFacade libraryCardFacade = new LibraryCardConfiguration().LibraryCardFacade();
        StatisticsFacade statisticsFacade = new StatisticsConfiguration().statisticsFacade();
        InMemoryPasswordEncoderAdapter passwordEncoder = new InMemoryPasswordEncoderAdapter();
        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();
        UserCredentialsService credentialsService = new UserCredentialsService(userRepository, passwordEncoder);
        UserService userService = new UserService(
                userRepository, credentialsService, authFacade, bookFacade, bookItemFacade, bookItemLoanFacade, bookItemRequestFacade,
                fineFacade, personFacade, libraryCardFacade, statisticsFacade
        );
        return new UserFacade(
                new RegisterUserUseCase(userRepository, personFacade, libraryCardFacade, credentialsService, publisher),
                new GetAllUsersUseCase(userService),
                new GetUserListUseCase(userService),
                new GetUserUseCase(userService),
                new GetUserPreviewUseCase(userService),
                new GetUserDetailsUseCase(userService),
                new GetUserDetailsAdminUseCase(userService, authFacade),
                new UpdateUserUseCase(userService),
                new UpdateUserByAdminUseCase(userService),
                new DeleteUserUseCase(userService, authFacade, bookItemRequestFacade),
                new VerifyUserForBookItemRequestUseCase(userService),
                new VerifyUserForBookItemLoanUseCase(userService),
                new VerifyUserForBookItemRenewalUseCase(userService),
                new CountAllUseCase(userService),
                new CountNewRegisteredUsersByMonthUseCase(userService),
                new GetUsersByLoanCountDescendingUseCase(userService)
        );
    }

    @Bean
    UserFacade userFacade(
            UserRepositoryPort userRepository,
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
                userRepository, credentialsService, authFacade, bookFacade, bookItemFacade, bookItemLoanFacade, bookItemRequestFacade,
                fineFacade, personFacade, libraryCardFacade, statisticsFacade
        );
        return new UserFacade(
                new RegisterUserUseCase(userRepository, personFacade, libraryCardFacade, credentialsService, publisher),
                new GetAllUsersUseCase(userService),
                new GetUserListUseCase(userService),
                new GetUserUseCase(userService),
                new GetUserPreviewUseCase(userService),
                new GetUserDetailsUseCase(userService),
                new GetUserDetailsAdminUseCase(userService, authFacade),
                new UpdateUserUseCase(userService),
                new UpdateUserByAdminUseCase(userService),
                new DeleteUserUseCase(userService, authFacade, bookItemRequestFacade),
                new VerifyUserForBookItemRequestUseCase(userService),
                new VerifyUserForBookItemLoanUseCase(userService),
                new VerifyUserForBookItemRenewalUseCase(userService),
                new CountAllUseCase(userService),
                new CountNewRegisteredUsersByMonthUseCase(userService),
                new GetUsersByLoanCountDescendingUseCase(userService)
        );
    }

//    @Bean
//    UserEventListenerImpl userEventListener(
//            UserRepository userRepository) {
//        UserService userService = new UserService(userRepository, bookItemLoanFacade, fineFacade);
//        return new UserEventListenerImpl(userService);
//    }
}
