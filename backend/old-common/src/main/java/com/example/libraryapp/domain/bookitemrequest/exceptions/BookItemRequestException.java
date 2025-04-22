package com.example.libraryapp.domain.bookitemrequest.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class BookItemRequestException extends LibraryAppException {
    public BookItemRequestException(MessageKey messageKey) {
        super(messageKey);
    }
}
