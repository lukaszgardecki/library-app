package com.example.libraryapp.core.book;

import com.example.libraryapp.domain.book.exceptions.BookNotFoundException;
import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.model.BookId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookUseCase {
    private final BookService bookService;

    Book execute(BookId id) {
        return bookService.getBookById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
