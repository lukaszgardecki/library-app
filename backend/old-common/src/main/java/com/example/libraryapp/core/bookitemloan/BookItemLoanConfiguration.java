package com.example.libraryapp.core.bookitemloan;

import com.example.libraryapp.core.auth.AuthenticationConfiguration;
import com.example.libraryapp.core.auth.AuthenticationFacade;
import com.example.libraryapp.core.book.BookConfiguration;
import com.example.libraryapp.core.book.BookFacade;
import com.example.libraryapp.core.bookitem.BookItemConfiguration;
import com.example.libraryapp.core.bookitem.BookItemFacade;
import com.example.libraryapp.core.bookitemrequest.BookItemRequestConfiguration;
import com.example.libraryapp.core.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.core.fine.FineConfiguration;
import com.example.libraryapp.core.fine.FineFacade;
import com.example.libraryapp.core.user.UserConfiguration;
import com.example.libraryapp.core.user.UserFacade;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepositoryPort;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryBookItemLoanRepositoryAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryEventPublisherAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryUserRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemLoanConfiguration {

    public BookItemLoanFacade bookItemLoanFacade() {
        InMemoryBookItemLoanRepositoryAdapter loanRepository = new InMemoryBookItemLoanRepositoryAdapter();
        BookItemLoanService loanService = new BookItemLoanService(loanRepository);
        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
        AuthenticationFacade authFacade = new AuthenticationConfiguration().authenticationFacade(userRepository);
        BookFacade bookFacade = new BookConfiguration().bookFacade();
        BookItemFacade bookItemFacade = new BookItemConfiguration().bookItemFacade();
        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
        FineFacade fineFacade = new FineConfiguration().fineFacade();
        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();

        return new BookItemLoanFacade(
                new GetBookItemLoanUseCase(loanService),
                new GetPageOfBookItemLoansByParamsUseCase(loanRepository, bookItemRequestFacade),
                new GetPageOfBookItemLoanListPreviewsUseCase(loanRepository, bookItemRequestFacade),
                new GetAllUserLoansUseCase(loanService),
                new GetUserCurrentLoansUseCase(loanRepository),
                new GetTopSubjectsWithLoansCountUseCase(loanService),
                new BorrowBookItemUseCase(
                        userFacade, authFacade, bookItemRequestFacade, bookItemFacade, bookFacade,
                        loanService, fineFacade, publisher
                ),
                new RenewBookItemLoanUseCase(
                        userFacade, authFacade, bookItemFacade, bookItemRequestFacade, bookFacade, loanService, fineFacade, publisher
                ),
                new ReturnBookItemUseCase(
                        authFacade, bookItemFacade, bookFacade, loanService, fineFacade, publisher
                ),
                new ProcessLostBookItemUseCase(
                        authFacade, bookItemFacade, bookFacade, loanService, fineFacade, publisher
                ),
                new CountByCreationDateUseCase(loanService),
                new CountUniqueBorrowersInCurrentMonthUseCase(loanService),
                new CountBookItemLoansMonthlyUseCase(loanService),
                new CountBookItemLoansDailyUseCase(loanService)
        );
    }

    @Bean
    BookItemLoanFacade bookItemLoanFacade(
            BookItemLoanRepositoryPort loanRepository,
            UserFacade userFacade,
            AuthenticationFacade authFacade,
            BookItemRequestFacade bookItemRequestFacade,
            BookItemFacade bookItemFacade,
            BookFacade bookFacade,
            FineFacade fineFacade,
            EventPublisherPort publisher
    ) {
        BookItemLoanService loanService = new BookItemLoanService(loanRepository);
        return new BookItemLoanFacade(
                new GetBookItemLoanUseCase(loanService),
                new GetPageOfBookItemLoansByParamsUseCase(loanRepository, bookItemRequestFacade),
                new GetPageOfBookItemLoanListPreviewsUseCase(loanRepository, bookItemRequestFacade),
                new GetAllUserLoansUseCase(loanService),
                new GetUserCurrentLoansUseCase(loanRepository),
                new GetTopSubjectsWithLoansCountUseCase(loanService),
                new BorrowBookItemUseCase(
                        userFacade, authFacade, bookItemRequestFacade, bookItemFacade, bookFacade,
                        loanService, fineFacade, publisher
                ),
                new RenewBookItemLoanUseCase(
                        userFacade, authFacade, bookItemFacade, bookItemRequestFacade, bookFacade, loanService, fineFacade, publisher
                ),
                new ReturnBookItemUseCase(
                        authFacade, bookItemFacade, bookFacade, loanService, fineFacade, publisher
                ),
                new ProcessLostBookItemUseCase(
                        authFacade, bookItemFacade, bookFacade, loanService, fineFacade, publisher
                ),
                new CountByCreationDateUseCase(loanService),
                new CountUniqueBorrowersInCurrentMonthUseCase(loanService),
                new CountBookItemLoansMonthlyUseCase(loanService),
                new CountBookItemLoansDailyUseCase(loanService)
        );
    }
}
