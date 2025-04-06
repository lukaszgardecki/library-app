package com.example.libraryapp.domain.shelf.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class ShelfException extends LibraryAppException {

    public ShelfException(MessageKey messageKey) {
        super(messageKey);
    }
}
