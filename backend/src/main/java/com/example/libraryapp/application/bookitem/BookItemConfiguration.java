package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.application.book.BookConfiguration;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestConfiguration;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryBookItemRepositoryAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryEventPublisherAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemConfiguration {

    public BookItemFacade bookItemFacade() {
        InMemoryBookItemRepositoryAdapter repository = new InMemoryBookItemRepositoryAdapter();
        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();
        BookFacade bookFacade = new BookConfiguration().bookFacade();
        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
        BookItemBarcodeGenerator barcodeGenerator = new BookItemBarcodeGenerator();
        BookItemService bookItemService = new BookItemService(
                repository, bookItemRequestFacade, barcodeGenerator, bookFacade
        );
        return new BookItemFacade(
                new GetBookItemUseCase(repository),
                new GetPageOfBookItemsUseCase(repository),
                new AddBookItemUseCase(bookItemService),
                new UpdateBookItemUseCase(bookItemService),
                new DeleteBookItemUseCase(repository, bookItemService, publisher),
                new VerifyAndGetBookItemForRequestUseCase(bookItemService),
                new VerifyAndGetBookItemForLoanUseCase(bookItemService)
        );
    }

    @Bean
    BookItemFacade bookItemFacade(
            BookItemRepositoryPort repository,
            BookFacade bookFacade,
            BookItemRequestFacade bookItemRequestFacade,
            EventPublisherPort publisher
    ) {
        BookItemService bookItemService = new BookItemService(
                repository, bookItemRequestFacade, new BookItemBarcodeGenerator(), bookFacade
        );
        return new BookItemFacade(
                new GetBookItemUseCase(repository),
                new GetPageOfBookItemsUseCase(repository),
                new AddBookItemUseCase(bookItemService),
                new UpdateBookItemUseCase(bookItemService),
                new DeleteBookItemUseCase(repository, bookItemService, publisher),
                new VerifyAndGetBookItemForRequestUseCase(bookItemService),
                new VerifyAndGetBookItemForLoanUseCase(bookItemService)
        );
    }
}
