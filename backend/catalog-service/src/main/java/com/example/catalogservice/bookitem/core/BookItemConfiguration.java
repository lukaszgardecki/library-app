package com.example.catalogservice.bookitem.core;

import com.example.catalogservice.book.core.BookFacade;
import com.example.catalogservice.bookitem.domain.ports.BookItemRepositoryPort;
import com.example.catalogservice.bookitem.domain.ports.BookItemRequestServicePort;
import com.example.catalogservice.bookitem.domain.ports.EventPublisherPort;
import com.example.catalogservice.bookitem.domain.ports.WarehouseServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemConfiguration {

//    public BookItemFacade bookItemFacade() {
//        InMemoryBookItemRepositoryAdapter repository = new InMemoryBookItemRepositoryAdapter();
//        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();
//        BookFacade bookFacade = new BookConfiguration().bookFacade();
//        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
//        BookItemBarcodeGenerator barcodeGenerator = new BookItemBarcodeGenerator();
//        WarehouseFacade warehouseFacade = new WarehouseConfiguration().warehouseFacade();
//        BookItemService bookItemService = new BookItemService(
//                repository, bookItemRequestFacade, barcodeGenerator, bookFacade, warehouseFacade
//        );
//        return new BookItemFacade(
//                new GetBookItemUseCase(repository),
//                new GetPageOfBookItemsUseCase(bookItemService),
//                new AddBookItemUseCase(bookItemService),
//                new UpdateBookItemUseCase(bookItemService),
//                new DeleteBookItemUseCase(repository, bookItemService, publisher),
//                new CountByParamsUseCase(repository),
//                new VerifyAndGetBookItemForRequestUseCase(bookItemService),
//                new VerifyAndGetBookItemForLoanUseCase(bookItemService)
//        );
//    }

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
            BookFacade bookFacade,
            WarehouseServicePort warehouseService,
            BookItemRequestServicePort bookItemRequestService
    ) {
        return new BookItemService(
                repository, bookItemRequestService, warehouseService, new BookItemBarcodeGenerator(), bookFacade
        );
    }
}
