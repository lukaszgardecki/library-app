package com.example.catalogservice.core.book;

import com.example.catalogservice.domain.ports.out.BookRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfiguration {

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
