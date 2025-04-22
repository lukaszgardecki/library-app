package com.example.libraryapp.domain.bookitem.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class BookItemNotFoundException extends LibraryAppNotFoundException {

    public BookItemNotFoundException(BookItemId bookItemId) {
        super(MessageKey.BOOK_ITEM_NOT_FOUND_ID, bookItemId.value().toString());
    }
}
