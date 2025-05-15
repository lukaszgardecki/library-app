package com.example.requestservice.core;

import com.example.requestservice.domain.ports.in.EventListenerPort;
import com.example.requestservice.domain.ports.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemRequestConfiguration {

    @Bean
    public BookItemRequestFacade bookItemRequestFacade(
            BookItemRequestService bookItemRequestService,
            BookItemRequestRepositoryPort bookItemRequestRepository,
            UserServicePort userService,
            CatalogServicePort catalogService,
            EventPublisherPort publisher,
            SourceValidatorPort sourceValidator,
            WebSocketSenderPort webSocketSender
    ) {
        return new BookItemRequestFacade(
                new GetBookItemRequestUseCase(bookItemRequestService, sourceValidator),
                new GetCurrentBookItemRequestUseCase(bookItemRequestService),
                new GetUserCurrentBookItemRequestsUseCase(bookItemRequestService),
                new GetPageOfBookItemRequestsByStatusUseCase(bookItemRequestRepository),
                new CreateBookItemRequestUseCase(
                        userService, catalogService, bookItemRequestService, webSocketSender, publisher
                ),
                new CancelAllBookItemRequestsUseCase(
                        bookItemRequestService, catalogService, publisher
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
