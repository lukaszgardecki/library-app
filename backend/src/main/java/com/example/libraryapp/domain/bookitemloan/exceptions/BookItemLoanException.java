package com.example.libraryapp.domain.bookitemloan.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class BookItemLoanException extends LibraryAppException {

    public BookItemLoanException(MessageKey messageKey) {
        super(messageKey);
    }
}
