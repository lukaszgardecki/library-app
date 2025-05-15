package com.example.catalogservice.domain.exception;

import com.example.catalogservice.domain.i18n.MessageKey;
import com.example.catalogservice.domain.model.book.values.BookId;

public class BookNotFoundException extends LibraryAppNotFoundException {

    public BookNotFoundException(BookId bookId) {
        super(MessageKey.BOOK_NOT_FOUND_ID, bookId.value().toString());
    }
}
