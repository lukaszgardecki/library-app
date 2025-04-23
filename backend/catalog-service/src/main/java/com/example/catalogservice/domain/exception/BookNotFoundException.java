package com.example.catalogservice.domain.exception;

import com.example.catalogservice.domain.model.book.BookId;
import com.example.catalogservice.domain.MessageKey;

public class BookNotFoundException extends LibraryAppNotFoundException {

    public BookNotFoundException(BookId bookId) {
        super(MessageKey.BOOK_NOT_FOUND_ID, bookId.value().toString());
    }
}
