package com.example.catalogservice.book.core;

import com.example.catalogservice.book.domain.ports.BookRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfiguration {

//    public BookFacade bookFacade() {
//        InMemoryBookRepositoryAdapter bookRepository = new InMemoryBookRepositoryAdapter();
//        BookService bookService = new BookService(bookRepository);
//        return new BookFacade(
//                new GetAllBooksUseCase(bookService),
//                new GetBookUseCase(bookService),
//                new AddBookUseCase(bookService),
//                new UpdateBookUseCase(bookService),
//                new DeleteBookUseCase(bookRepository)
//        );
//    }

    @Bean
    BookFacade bookFacade(BookRepositoryPort bookRepository) {
        BookService bookService = new BookService(bookRepository);
        return new BookFacade(
                new GetAllBooksUseCase(bookService),
                new GetBookUseCase(bookService),
                new AddBookUseCase(bookService),
                new UpdateBookUseCase(bookService),
                new DeleteBookUseCase(bookRepository)
        );
    }
}
