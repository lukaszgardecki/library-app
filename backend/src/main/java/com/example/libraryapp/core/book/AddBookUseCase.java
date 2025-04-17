package com.example.libraryapp.core.book;

import com.example.libraryapp.domain.book.model.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddBookUseCase {
    private final BookService bookService;

    Book execute(Book book) {
        return bookService.save(book);
    }
}
