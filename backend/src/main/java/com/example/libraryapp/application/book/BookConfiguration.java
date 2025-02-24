package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.ports.BookRepositoryPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryBookRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfiguration {

    public BookFacade bookFacade() {
        InMemoryBookRepositoryAdapter bookRepository = new InMemoryBookRepositoryAdapter();
        BookService bookService = new BookService(bookRepository);
        return new BookFacade(
                new GetBookUseCase(bookService),
                new AddBookUseCase(bookService),
                new UpdateBookUseCase(bookService),
                new DeleteBookUseCase(bookRepository)
        );
    }

    @Bean
    BookFacade bookFacade(BookRepositoryPort bookRepository) {
        BookService bookService = new BookService(bookRepository);
        return new BookFacade(
                new GetBookUseCase(bookService),
                new AddBookUseCase(bookService),
                new UpdateBookUseCase(bookService),
                new DeleteBookUseCase(bookRepository)
        );
    }
}
