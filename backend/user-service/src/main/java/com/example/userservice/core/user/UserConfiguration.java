package com.example.userservice.core.user;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.domain.ports.in.EventListenerPort;
import com.example.userservice.domain.ports.out.BookItemLoanServicePort;
import com.example.userservice.domain.ports.out.BookItemRequestServicePort;
import com.example.userservice.domain.ports.out.FineServicePort;
import com.example.userservice.domain.ports.out.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    UserFacade userFacade(
            UserRepositoryPort userRepository,
            BookItemRequestServicePort bookItemRequestService,
            LibraryCardFacade libraryCardFacade,
            UserService userService
    ) {
        return new UserFacade(
                new CreateUserUseCase(userRepository, libraryCardFacade),
                new GetAllUsersUseCase(userService),
                new GetUserUseCase(userService),
                new UpdateUserUseCase(userService),
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
            BookItemLoanServicePort bookItemLoanService,
            FineServicePort fineService,
            LibraryCardFacade libraryCardFacade
    ) {
        return new UserService(bookItemLoanService, userRepository, fineService, personFacade, libraryCardFacade);
    }

    @Bean
    EventListenerPort eventListenerService(UserRepositoryPort userRepository) {
        return new EventListenerService(userRepository);
    }
}
