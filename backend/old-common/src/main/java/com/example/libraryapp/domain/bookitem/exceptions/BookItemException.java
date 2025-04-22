package com.example.libraryapp.domain.bookitem.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class BookItemException extends LibraryAppException {

    public BookItemException(MessageKey messageKey) {
        super(messageKey);
    }
}
