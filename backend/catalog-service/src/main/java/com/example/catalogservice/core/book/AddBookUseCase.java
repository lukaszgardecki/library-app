package com.example.catalogservice.core.book;

import com.example.catalogservice.domain.model.book.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddBookUseCase {
    private final BookService bookService;

    Book execute(Book book) {
        return bookService.save(book);
    }
}
