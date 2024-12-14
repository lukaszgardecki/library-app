package com.example.libraryapp.NEWapplication.book;

import com.example.libraryapp.NEWdomain.book.model.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddBookUseCase {
    private final BookService bookService;

    Book execute(Book book) {
        return bookService.save(book);
    }
}
