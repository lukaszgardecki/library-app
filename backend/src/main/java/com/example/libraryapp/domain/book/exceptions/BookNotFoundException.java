package com.example.libraryapp.domain.book.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class BookNotFoundException extends LibraryAppNotFoundException {

    public BookNotFoundException(Long bookId) {
        super(MessageKey.BOOK_NOT_FOUND_ID, bookId.toString());
    }
}
