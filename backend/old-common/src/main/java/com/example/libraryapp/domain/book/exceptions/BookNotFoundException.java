package com.example.libraryapp.domain.book.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class BookNotFoundException extends LibraryAppNotFoundException {

    public BookNotFoundException(BookId bookId) {
        super(MessageKey.BOOK_NOT_FOUND_ID, bookId.value().toString());
    }
}
