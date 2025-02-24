package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.application.auth.AuthenticationConfiguration;
import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookConfiguration;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemConfiguration;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.user.UserConfiguration;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepositoryPort;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryBookItemRequestRepositoryAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryEventPublisherAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryUserRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class BookItemRequestConfiguration {

    public BookItemRequestFacade bookItemRequestFacade() {
        InMemoryBookItemRequestRepositoryAdapter bookItemRequestRepository = new InMemoryBookItemRequestRepositoryAdapter();
        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
        AuthenticationFacade authFacade = new AuthenticationConfiguration().authenticationFacade(userRepository);
        BookFacade bookFacade = new BookConfiguration().bookFacade();
        BookItemFacade bookItemFacade = new BookItemConfiguration().bookItemFacade();
        BookItemRequestService bookItemRequestService = new BookItemRequestService(bookItemRequestRepository);
        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();

        return new BookItemRequestFacade(
                new GetCurrentBookItemRequestUseCase(bookItemRequestService),
                new GetUserCurrentBookItemRequestsUseCase(authFacade, bookItemRequestService),
                new GetPageOfBookItemRequestsByStatusUseCase(bookItemRequestRepository),
                new CreateBookItemRequestUseCase(
                        userFacade, authFacade, bookItemRequestService, bookItemFacade, bookFacade, publisher
                ),
                new CancelBookItemRequestUseCase(
                        authFacade, bookItemRequestService, bookItemFacade, bookFacade, publisher
                ),
                new ChangeBookItemRequestStatusUseCase(bookItemRequestRepository),
                new ChangeBookItemRequestStatusToReadyUseCase(
                        bookItemRequestService, bookItemRequestRepository, publisher
                ),
                new CheckIfBookItemRequestStatusIsReadyUseCase(bookItemRequestService),
                new EnsureBookItemNotRequestedUseCase(bookItemRequestRepository),
                new IsBookItemRequestedUseCase(bookItemRequestService)
        );
    }

    @Bean
    public BookItemRequestFacade bookItemRequestFacade(
            UserFacade userFacade,
            AuthenticationFacade authFacade,
            @Lazy BookItemFacade bookItemFacade,
            BookFacade bookFacade,
            BookItemRequestRepositoryPort bookItemRequestRepository,
            EventPublisherPort publisher
    ) {
        BookItemRequestService bookItemRequestService = new BookItemRequestService(bookItemRequestRepository);
        return new BookItemRequestFacade(
                new GetCurrentBookItemRequestUseCase(bookItemRequestService),
                new GetUserCurrentBookItemRequestsUseCase(authFacade, bookItemRequestService),
                new GetPageOfBookItemRequestsByStatusUseCase(bookItemRequestRepository),
                new CreateBookItemRequestUseCase(
                        userFacade, authFacade, bookItemRequestService, bookItemFacade, bookFacade, publisher
                ),
                new CancelBookItemRequestUseCase(
                        authFacade, bookItemRequestService, bookItemFacade, bookFacade, publisher
                ),
                new ChangeBookItemRequestStatusUseCase(bookItemRequestRepository),
                new ChangeBookItemRequestStatusToReadyUseCase(
                        bookItemRequestService, bookItemRequestRepository, publisher
                ),
                new CheckIfBookItemRequestStatusIsReadyUseCase(bookItemRequestService),
                new EnsureBookItemNotRequestedUseCase(bookItemRequestRepository),
                new IsBookItemRequestedUseCase(bookItemRequestService)
        );
    }
}
