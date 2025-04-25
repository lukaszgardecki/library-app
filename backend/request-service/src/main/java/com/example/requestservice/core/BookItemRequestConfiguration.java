package com.example.requestservice.core;

import com.example.requestservice.domain.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemRequestConfiguration {

//    public BookItemRequestFacade bookItemRequestFacade() {
//        InMemoryBookItemRequestRepositoryAdapter bookItemRequestRepository = new InMemoryBookItemRequestRepositoryAdapter();
//        InMemoryUserRepositoryAdapter userRepository = new InMemoryUserRepositoryAdapter();
//        UserFacade userFacade = new UserConfiguration().userFacade(userRepository);
//        AuthenticationFacade authFacade = new AuthenticationConfiguration().authenticationFacade(userRepository);
//        BookFacade bookFacade = new BookConfiguration().bookFacade();
//        BookItemFacade bookItemFacade = new BookItemConfiguration().bookItemFacade();
//        BookItemRequestService bookItemRequestService = new BookItemRequestService(bookItemRequestRepository);
//        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();
//
//        return new BookItemRequestFacade(
//                new GetCurrentBookItemRequestUseCase(bookItemRequestService),
//                new GetUserCurrentBookItemRequestsUseCase(authFacade, bookItemRequestService),
//                new GetPageOfBookItemRequestsByStatusUseCase(bookItemRequestRepository),
//                new CreateBookItemRequestUseCase(
//                        userFacade, authFacade, bookItemRequestService, bookItemFacade, bookFacade, publisher
//                ),
//                new CancelBookItemRequestUseCase(
//                        authFacade, bookItemRequestService, bookItemFacade, bookFacade, publisher
//                ),
//                new ChangeBookItemRequestStatusUseCase(bookItemRequestRepository),
//                new ChangeBookItemRequestStatusToReadyUseCase(
//                        bookItemRequestService, bookItemRequestRepository, publisher
//                ),
//                new CheckIfBookItemRequestStatusIsReadyUseCase(bookItemRequestService),
//                new EnsureBookItemNotRequestedUseCase(bookItemRequestRepository),
//                new IsBookItemRequestedUseCase(bookItemRequestService)
//        );
//    }

    @Bean
    public BookItemRequestFacade bookItemRequestFacade(
            BookItemRequestService bookItemRequestService,
            BookItemRequestRepositoryPort bookItemRequestRepository,
            UserServicePort userService,
            CatalogServicePort catalogService,
            EventPublisherPort publisher
    ) {
        return new BookItemRequestFacade(
                new GetCurrentBookItemRequestUseCase(bookItemRequestService),
                new GetUserCurrentBookItemRequestsUseCase(bookItemRequestService),
                new GetPageOfBookItemRequestsByStatusUseCase(bookItemRequestRepository),
                new CreateBookItemRequestUseCase(
                        userService, catalogService, bookItemRequestService, publisher
                ),
                new CancelBookItemRequestUseCase(
                        bookItemRequestService, publisher
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
    public BookItemRequestService bookItemRequestService(BookItemRequestRepositoryPort bookItemRequestRepository) {
        return new BookItemRequestService(bookItemRequestRepository);
    }

    @Bean
    public EventListenerPort eventListenerPort(
            BookItemRequestService bookItemRequestService,
            BookItemRequestRepositoryPort bookItemRequestRepository,
            EventPublisherPort publisher
    ) {
        return new EventListenerService(
                bookItemRequestService, bookItemRequestRepository, publisher
        );
    }
}
