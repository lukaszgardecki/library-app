package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.ports.BookItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookItemConfiguration {

    @Bean
    BookItemFacade bookItemFacade(BookItemRepository repository) {
        BookItemService bookItemService = new BookItemService(repository, new BookItemBarcodeGenerator());
        return new BookItemFacade(
                new GetBookItemUseCase(repository),
                new GetPageOfBookItemsUseCase(repository),
                new GetPageOfBookItemsByBookIdUseCase(repository),
                new AddBookItemUseCase(bookItemService),
                new UpdateBookItemUseCase(bookItemService),
                new UpdateBookItemAfterBookRequestUseCase(bookItemService),
                new UpdateBookItemAfterLoanUseCase(bookItemService),
                new DeleteBookItemUseCase(repository),
                new VerifyAndGetBookItemForRequestUseCase(bookItemService),
                new VerifyAndGetBookItemForLoanUseCase(bookItemService)
        );
    }

    @Bean
    BookItemEventHandler bookItemEventHandler() {
        return new BookItemEventHandler();
    }
}
