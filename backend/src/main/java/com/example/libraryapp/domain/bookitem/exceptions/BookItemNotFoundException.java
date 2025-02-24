package com.example.libraryapp.domain.bookitem.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class BookItemNotFoundException extends LibraryAppNotFoundException {

    public BookItemNotFoundException(Long bookItemId) {
        super(MessageKey.BOOK_ITEM_NOT_FOUND_ID, bookItemId.toString());
    }
}
