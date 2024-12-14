package com.example.libraryapp.NEWapplication.book;

import com.example.libraryapp.NEWdomain.book.ports.BookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookConfiguration {

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
