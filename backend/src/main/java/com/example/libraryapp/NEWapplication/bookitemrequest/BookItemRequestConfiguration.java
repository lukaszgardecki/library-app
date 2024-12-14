package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWapplication.book.BookFacade;
import com.example.libraryapp.NEWapplication.bookitem.BookItemFacade;
import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.bookitemrequest.ports.BookItemRequestRepository;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookItemRequestConfiguration {

    @Bean
    BookItemRequestFacade bookItemRequestFacade(
            UserFacade userFacade,
            AuthenticationFacade authFacade,
            BookItemFacade bookItemFacade,
            BookFacade bookFacade,
            BookItemRequestRepository bookItemRequestRepository,
            EventPublisherPort publisher
    ) {
        BookItemRequestService bookItemRequestService = new BookItemRequestService(bookItemRequestRepository);
        return new BookItemRequestFacade(
                new GetBookItemRequestByParamsUseCase(bookItemRequestService),
                new GetPageOfBookItemRequestsByStatusUseCase(bookItemRequestRepository),
                new CreateBookItemRequestUseCase(
                        userFacade, authFacade, bookItemRequestService,
                        bookItemFacade, bookFacade, publisher
                ),
                new ChangeBookItemRequestStatusUseCase(bookItemRequestRepository),
                new ChangeBookItemRequestStatusToReadyUseCase(
                        bookItemRequestService, bookItemRequestRepository, publisher
                ),
                new CheckBookItemRequestStatusUseCase(bookItemRequestService),
                new EnsureBookItemNotRequestedUseCase(bookItemRequestRepository),
                new IsBookItemRequestedUseCase(bookItemRequestRepository)
        );
    }
}
