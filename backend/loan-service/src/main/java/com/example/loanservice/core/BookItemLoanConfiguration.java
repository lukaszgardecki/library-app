package com.example.loanservice.core;

import com.example.loanservice.domain.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemLoanConfiguration {

    @Bean
    BookItemLoanFacade bookItemLoanFacade(
            BookItemLoanRepositoryPort loanRepository,
            UserServicePort userService,
            CatalogServicePort catalogService,
            BookItemRequestServicePort bookItemRequestService,
            FineServicePort fineService,
            EventPublisherPort publisher,
            SourceValidator sourceValidator
    ) {
        BookItemLoanService loanService = new BookItemLoanService(loanRepository);
        return new BookItemLoanFacade(
                new GetBookItemLoanUseCase(loanService, sourceValidator),
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
