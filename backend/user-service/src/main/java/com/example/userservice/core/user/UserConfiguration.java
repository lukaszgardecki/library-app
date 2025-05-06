package com.example.userservice.core.user;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.domain.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    UserFacade userFacade(
            UserRepositoryPort userRepository,
            PersonFacade personFacade,
            BookItemRequestServicePort bookItemRequestService,
            LibraryCardFacade libraryCardFacade,
            UserService userService
    ) {
        return new UserFacade(
                new CreateUserUseCase(userRepository, personFacade, libraryCardFacade),
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
