package com.example.loanservice.core;

import com.example.loanservice.domain.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemLoanConfiguration {

//    public BookItemLoanFacade bookItemLoanFacade() {
//        InMemoryBookItemLoanRepositoryAdapter loanRepository = new InMemoryBookItemLoanRepositoryAdapter();
//        BookItemLoanService loanService = new BookItemLoanService(loanRepository);
//        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
//        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
//        AuthenticationFacade authFacade = new AuthenticationConfiguration().authenticationFacade(userRepository);
//        BookFacade bookFacade = new BookConfiguration().bookFacade();
//        BookItemFacade bookItemFacade = new BookItemConfiguration().bookItemFacade();
//        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
//        FineFacade fineFacade = new FineConfiguration().fineFacade();
//        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();
//
//        return new BookItemLoanFacade(
//                new GetBookItemLoanUseCase(loanService),
//                new GetPageOfBookItemLoansByParamsUseCase(loanRepository, bookItemRequestFacade),
//                new GetPageOfBookItemLoanListPreviewsUseCase(loanRepository, bookItemRequestFacade),
//                new GetAllUserLoansUseCase(loanService),
//                new GetUserCurrentLoansUseCase(loanRepository),
//                new GetTopSubjectsWithLoansCountUseCase(loanService),
//                new BorrowBookItemUseCase(
//                        userFacade, authFacade, bookItemRequestFacade, bookItemFacade, bookFacade,
//                        loanService, fineFacade, publisher
//                ),
//                new RenewBookItemLoanUseCase(
//                        userFacade, authFacade, bookItemFacade, bookItemRequestFacade, bookFacade, loanService, fineFacade, publisher
//                ),
//                new ReturnBookItemUseCase(
//                        authFacade, bookItemFacade, bookFacade, loanService, fineFacade, publisher
//                ),
//                new ProcessLostBookItemUseCase(
//                        authFacade, bookItemFacade, bookFacade, loanService, fineFacade, publisher
//                ),
//                new CountByCreationDateUseCase(loanService),
//                new CountUniqueBorrowersInCurrentMonthUseCase(loanService),
//                new CountBookItemLoansMonthlyUseCase(loanService),
//                new CountBookItemLoansDailyUseCase(loanService)
//        );
//    }

    @Bean
    BookItemLoanFacade bookItemLoanFacade(
            BookItemLoanRepositoryPort loanRepository,
            UserServicePort userService,
            CatalogServicePort catalogService,
            BookItemRequestServicePort bookItemRequestService,
            FineServicePort fineService,
            EventPublisherPort publisher
    ) {
        BookItemLoanService loanService = new BookItemLoanService(loanRepository);
        return new BookItemLoanFacade(
                new GetBookItemLoanUseCase(loanService),
                new GetPageOfBookItemLoansByParamsUseCase(loanRepository, bookItemRequestService),
                new GetPageOfBookItemLoanListPreviewsUseCase(loanRepository, bookItemRequestService),
                new GetAllUserLoansUseCase(loanService),
                new GetUserCurrentLoansUseCase(loanRepository),
                new GetTopSubjectsWithLoansCountUseCase(loanService),
                new BorrowBookItemUseCase(
                        userService, bookItemRequestService, catalogService, loanService, fineService, publisher
                ),
                new RenewBookItemLoanUseCase(userService, bookItemRequestService, loanService, fineService, publisher),
                new ReturnBookItemUseCase(loanService, publisher),
                new ProcessLostBookItemUseCase(catalogService, loanService, publisher),
                new CountByCreationDateUseCase(loanService),
                new CountUniqueBorrowersInCurrentMonthUseCase(loanService),
                new CountBookItemLoansMonthlyUseCase(loanService),
                new CountBookItemLoansDailyUseCase(loanService)
        );
    }

    @Bean
    EventListenerPort eventListenerService(BookItemLoanRepositoryPort loanRepository,EventPublisherPort publisher) {
        BookItemLoanService loanService = new BookItemLoanService(loanRepository);
        return new EventListenerService(loanService, publisher);
    }
}
