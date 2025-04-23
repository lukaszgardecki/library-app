package com.example.catalogservice.domain.exception;

import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.MessageKey;

public class BookItemNotFoundException extends LibraryAppNotFoundException {

    public BookItemNotFoundException(BookItemId bookItemId) {
        super(MessageKey.BOOK_ITEM_NOT_FOUND_ID, bookItemId.value().toString());
    }
}
