package com.example.catalogservice.domain.exception;

import com.example.catalogservice.domain.i18n.MessageKey;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;

public class BookItemNotFoundException extends LibraryAppNotFoundException {

    public BookItemNotFoundException(BookItemId bookItemId) {
        super(MessageKey.BOOK_ITEM_NOT_FOUND_ID, bookItemId.value().toString());
    }
}
