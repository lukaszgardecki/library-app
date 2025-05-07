package com.example.catalogservice.core.book;

import com.example.catalogservice.domain.exception.BookNotFoundException;
import com.example.catalogservice.domain.model.book.Book;
import com.example.catalogservice.domain.model.book.values.BookId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookUseCase {
    private final BookService bookService;

    Book execute(BookId id) {
        return bookService.getBookById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
