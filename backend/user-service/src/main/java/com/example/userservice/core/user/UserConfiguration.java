package com.example.userservice.core.user;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.domain.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class UserConfiguration {

//    public UserFacade userFacade() {
//        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
//        return userFacade(userRepository);
//    }

//    public UserFacade userFacade(UserRepositoryPort userRepository) {
//        InMemoryPersonRepositoryAdapter personRepository = new InMemoryPersonRepositoryAdapter();
//        PersonFacade personFacade = new PersonConfiguration().personFacade(personRepository);
//        AuthenticationFacade authFacade = new AuthenticationConfiguration().authenticationFacade(userRepository);
//        BookFacade bookFacade = new BookConfiguration().bookFacade();
//        BookItemFacade bookItemFacade = new BookItemConfiguration().bookItemFacade();
//        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
//        BookItemLoanFacade bookItemLoanFacade = new BookItemLoanConfiguration().bookItemLoanFacade();
//        FineFacade fineFacade = new FineConfiguration().fineFacade();
//        LibraryCardFacade libraryCardFacade = new LibraryCardConfiguration().LibraryCardFacade();
//        StatisticsFacade statisticsFacade = new StatisticsConfiguration().statisticsFacade();
//        InMemoryPasswordEncoderAdapter passwordEncoder = new InMemoryPasswordEncoderAdapter();
//        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();
//        UserCredentialsService credentialsService = new UserCredentialsService(userRepository, passwordEncoder);
//        UserService userService = new UserService(
//                userRepository, credentialsService, bookFacade, bookItemFacade, bookItemLoanFacade, bookItemRequestFacade,
//                fineFacade, personFacade, libraryCardFacade, statisticsFacade
//        );
//        return new UserFacade(
//                new RegisterUserUseCase(userRepository, personFacade, libraryCardFacade, credentialsService, publisher),
//                new GetAllUsersUseCase(userService),
//                new GetUserListUseCase(userService),
//                new GetUserUseCase(userService),
//                new GetUserPreviewUseCase(userService),
//                new GetUserDetailsUseCase(userService),
//                new GetUserDetailsAdminUseCase(userService, authFacade),
//                new UpdateUserUseCase(userService),
//                new UpdateUserByAdminUseCase(userService),
//                new DeleteUserUseCase(userService, bookItemRequestFacade),
//                new VerifyUserForBookItemRequestUseCase(userService),
//                new VerifyUserForBookItemLoanUseCase(userService),
//                new VerifyUserForBookItemRenewalUseCase(userService),
//                new CountAllUseCase(userService),
//                new CountNewRegisteredUsersByMonthUseCase(userService),
//                new GetUsersByLoanCountDescendingUseCase(userService)
//        );
//    }

    @Bean
    UserFacade userFacade(
            UserRepositoryPort userRepository,
            PersonFacade personFacade,
            @Lazy BookItemRequestServicePort bookItemRequestService,
            // TODO: 19.04.2025 sprawdzić czy to lazy jest potrzebne
            LibraryCardFacade libraryCardFacade,
            UserService userService,
            EventPublisherPort publisher
    ) {
        return new UserFacade(
                new CreateUserUseCase(userRepository, personFacade, libraryCardFacade, publisher),
                new GetAllUsersUseCase(userService),
                new GetUserListUseCase(userService),
                new GetUserUseCase(userService),
                new GetUserPreviewUseCase(userService),
                new GetUserDetailsUseCase(userService),
                new GetUserDetailsAdminUseCase(userService),
                new UpdateUserUseCase(userService),
                new UpdateUserByAdminUseCase(userService),
                new DeleteUserUseCase(userService, bookItemRequestService),
                new VerifyUserForBookItemRequestUseCase(userService),
                new VerifyUserForBookItemLoanUseCase(userService),
                new VerifyUserForBookItemRenewalUseCase(userService),
                new CountAllUseCase(userService),
                new CountNewRegisteredUsersByMonthUseCase(userService),
                new GetUsersByLoanCountDescendingUseCase(userService)
        );
    }

    @Bean
    UserService userService(
            UserRepositoryPort userRepository,
            PersonFacade personFacade,
            AuthenticationServicePort authService,
            BookCatalogServicePort bookCatalogService,
            BookItemRequestServicePort bookItemRequestService,
            BookItemLoanServicePort bookItemLoanService,
            StatisticsServicePort statisticsService,
            FineServicePort fineService,
            LibraryCardFacade libraryCardFacade
    ) {
        return new UserService(
                userRepository, authService, bookCatalogService, bookItemLoanService, bookItemRequestService,
                statisticsService, fineService, personFacade, libraryCardFacade
        );
    }

    @Bean
    EventListenerPort eventListenerService(UserRepositoryPort userRepository) {
        return new EventListenerService(userRepository);
    }
}
