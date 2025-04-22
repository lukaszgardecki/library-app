package com.example.catalogservice.bookitem.domain.exceptions;

import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.common.MessageKey;
import com.example.catalogservice.common.exception.LibraryAppNotFoundException;

public class BookItemNotFoundException extends LibraryAppNotFoundException {

    public BookItemNotFoundException(BookItemId bookItemId) {
        super(MessageKey.BOOK_ITEM_NOT_FOUND_ID, bookItemId.value().toString());
    }
}
