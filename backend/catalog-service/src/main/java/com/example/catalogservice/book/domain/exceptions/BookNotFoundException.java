package com.example.catalogservice.book.domain.exceptions;

import com.example.catalogservice.book.domain.model.BookId;
import com.example.catalogservice.common.MessageKey;
import com.example.catalogservice.common.exception.LibraryAppNotFoundException;

public class BookNotFoundException extends LibraryAppNotFoundException {

    public BookNotFoundException(BookId bookId) {
        super(MessageKey.BOOK_NOT_FOUND_ID, bookId.value().toString());
    }
}
