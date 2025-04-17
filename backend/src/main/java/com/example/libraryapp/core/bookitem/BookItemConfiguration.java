package com.example.libraryapp.core.bookitem;

import com.example.libraryapp.core.book.BookConfiguration;
import com.example.libraryapp.core.book.BookFacade;
import com.example.libraryapp.core.bookitemrequest.BookItemRequestConfiguration;
import com.example.libraryapp.core.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.core.warehouse.WarehouseConfiguration;
import com.example.libraryapp.core.warehouse.WarehouseFacade;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryBookItemRepositoryAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryEventPublisherAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class BookItemConfiguration {

    public BookItemFacade bookItemFacade() {
        InMemoryBookItemRepositoryAdapter repository = new InMemoryBookItemRepositoryAdapter();
        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();
        BookFacade bookFacade = new BookConfiguration().bookFacade();
        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
        BookItemBarcodeGenerator barcodeGenerator = new BookItemBarcodeGenerator();
        WarehouseFacade warehouseFacade = new WarehouseConfiguration().warehouseFacade();
        BookItemService bookItemService = new BookItemService(
                repository, bookItemRequestFacade, barcodeGenerator, bookFacade, warehouseFacade
        );
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
    BookItemFacade bookItemFacade(
            BookItemRepositoryPort repository,
            BookFacade bookFacade,
            @Lazy WarehouseFacade warehouseFacade,
            BookItemRequestFacade bookItemRequestFacade,
            EventPublisherPort publisher
    ) {
        BookItemService bookItemService = new BookItemService(
                repository, bookItemRequestFacade, new BookItemBarcodeGenerator(), bookFacade, warehouseFacade
        );
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
}
