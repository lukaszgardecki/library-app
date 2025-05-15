package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.core.book.BookFacade;
import com.example.catalogservice.domain.ports.in.EventListenerPort;
import com.example.catalogservice.domain.ports.out.BookItemRepositoryPort;
import com.example.catalogservice.domain.ports.out.BookItemRequestServicePort;
import com.example.catalogservice.domain.ports.out.EventPublisherPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemConfiguration {

    @Bean
    BookItemFacade bookItemFacade(
            BookItemRepositoryPort repository,
            BookItemService bookItemService,
            EventPublisherPort publisher
    ) {
        return new BookItemFacade(
                new GetBookItemUseCase(repository),
                new GetPageOfBookItemsUseCase(bookItemService),
                new AddBookItemUseCase(bookItemService),
                new UpdateBookItemUseCase(bookItemService),
                new DeleteBookItemUseCase(repository, bookItemService, publisher),
                new CountByParamsUseCase(repository),
                new VerifyAndGetBookItemForRequestUseCase(bookItemService),
                new VerifyAndGetBookItemForLoanUseCase(bookItemService)
        );
    }

    @Bean
    BookItemService bookItemService(
            BookItemRepositoryPort repository,
            BookFacade bookFacade
    ) {
        return new BookItemService(
                repository, new BookItemBarcodeGenerator(), bookFacade
        );
    }

    @Bean
    EventListenerPort eventListenerService(
            BookItemRepositoryPort bookItemRepository,
            BookItemRequestServicePort bookItemRequestService,
            BookItemService bookItemService
    ) {
        return new EventListenerService(bookItemRepository, bookItemRequestService, bookItemService);
    }
}
