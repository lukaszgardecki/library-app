package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.ports.BookRepository;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryBookRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfiguration {

    public BookFacade bookFacade() {
        InMemoryBookRepositoryImpl bookRepository = new InMemoryBookRepositoryImpl();
        BookService bookService = new BookService(bookRepository);
        return new BookFacade(
                new GetBookUseCase(bookService),
                new AddBookUseCase(bookService),
                new UpdateBookUseCase(bookService),
                new DeleteBookUseCase(bookRepository)
        );
    }

    @Bean
    BookFacade bookFacade(BookRepository bookRepository) {
        BookService bookService = new BookService(bookRepository);
        return new BookFacade(
                new GetBookUseCase(bookService),
                new AddBookUseCase(bookService),
                new UpdateBookUseCase(bookService),
                new DeleteBookUseCase(bookRepository)
        );
    }
}
