package com.example.libraryapp.NEWapplication.bookitemloan;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWapplication.book.BookFacade;
import com.example.libraryapp.NEWapplication.bookitem.BookItemFacade;
import com.example.libraryapp.NEWapplication.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.NEWapplication.fine.FineFacade;
import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.bookitemloan.ports.BookItemLoanRepository;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookItemLoanConfiguration {

    @Bean
    BookItemLoanFacade bookLoanFacade(
            BookItemLoanRepository loanRepository,
            UserFacade userFacade,
            AuthenticationFacade authFacade,
            BookItemRequestFacade bookItemRequestFacade,
            BookItemFacade bookItemFacade,
            BookFacade bookFacade,
            FineFacade fineFacade,
            EventPublisherPort publisher
    ) {
        BookItemLoanService loanService = new BookItemLoanService(loanRepository);
        BorrowBookItemUseCase borrowBookItemUseCase = new BorrowBookItemUseCase(
                userFacade,
                authFacade,
                bookItemRequestFacade,
                bookItemFacade,
                bookFacade,
                loanService,
                fineFacade,
                publisher
        );
        RenewBookItemLoanUseCase renewBookItemLoanUseCase = new RenewBookItemLoanUseCase(
                userFacade,
                authFacade,
                bookItemRequestFacade,
                bookItemFacade,
                bookFacade,
                loanService,
                fineFacade,
                publisher
        );
        ReturnBookItemUseCase returnBookItemUseCase = new ReturnBookItemUseCase(
                userFacade,
                authFacade,
                bookItemRequestFacade,
                bookItemFacade,
                bookFacade,
                loanService,
                fineFacade,
                publisher
        );

        return new BookItemLoanFacade(
                new GetBookItemLoanUseCase(loanService),
                new GetPageOfBookItemLoansByParamsUseCase(loanRepository, bookItemRequestFacade),
                borrowBookItemUseCase,
                renewBookItemLoanUseCase,
                returnBookItemUseCase
        );
    }
}
