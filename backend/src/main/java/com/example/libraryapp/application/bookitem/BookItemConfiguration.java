package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestConfiguration;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepository;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryBookItemRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemConfiguration {

    public BookItemFacade bookItemFacade() {
        InMemoryBookItemRepositoryImpl repository = new InMemoryBookItemRepositoryImpl();
        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
        BookItemBarcodeGenerator barcodeGenerator = new BookItemBarcodeGenerator();
        BookItemService bookItemService = new BookItemService(repository, bookItemRequestFacade, barcodeGenerator);

        return new BookItemFacade(
                new GetBookItemUseCase(repository),
                new GetPageOfBookItemsUseCase(repository),
                new GetPageOfBookItemsByBookIdUseCase(repository),
                new AddBookItemUseCase(bookItemService),
                new UpdateBookItemUseCase(bookItemService),
                new DeleteBookItemUseCase(repository),
                new VerifyAndGetBookItemForRequestUseCase(bookItemService),
                new VerifyAndGetBookItemForLoanUseCase(bookItemService)
        );
    }

    @Bean
    BookItemFacade bookItemFacade(
            BookItemRepository repository,
            BookItemRequestFacade bookItemRequestFacade
    ) {
        BookItemService bookItemService = new BookItemService(repository, bookItemRequestFacade, new BookItemBarcodeGenerator());
        return new BookItemFacade(
                new GetBookItemUseCase(repository),
                new GetPageOfBookItemsUseCase(repository),
                new GetPageOfBookItemsByBookIdUseCase(repository),
                new AddBookItemUseCase(bookItemService),
                new UpdateBookItemUseCase(bookItemService),
                new DeleteBookItemUseCase(repository),
                new VerifyAndGetBookItemForRequestUseCase(bookItemService),
                new VerifyAndGetBookItemForLoanUseCase(bookItemService)
        );
    }
}
