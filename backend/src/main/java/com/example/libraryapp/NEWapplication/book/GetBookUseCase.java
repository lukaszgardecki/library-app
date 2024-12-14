package com.example.libraryapp.NEWapplication.book;

import com.example.libraryapp.NEWdomain.book.exceptions.BookNotFoundException;
import com.example.libraryapp.NEWdomain.book.model.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookUseCase {
    private final BookService bookService;

    Book execute(Long id) {
        return bookService.getBook(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
