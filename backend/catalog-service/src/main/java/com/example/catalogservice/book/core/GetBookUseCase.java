package com.example.catalogservice.book.core;

import com.example.catalogservice.book.domain.exceptions.BookNotFoundException;
import com.example.catalogservice.book.domain.model.Book;
import com.example.catalogservice.book.domain.model.BookId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookUseCase {
    private final BookService bookService;

    Book execute(BookId id) {
        return bookService.getBookById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
