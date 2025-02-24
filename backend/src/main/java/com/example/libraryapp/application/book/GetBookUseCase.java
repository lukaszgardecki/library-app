package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.exceptions.BookNotFoundException;
import com.example.libraryapp.domain.book.model.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookUseCase {
    private final BookService bookService;

    Book execute(Long id) {
        return bookService.getBookById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
