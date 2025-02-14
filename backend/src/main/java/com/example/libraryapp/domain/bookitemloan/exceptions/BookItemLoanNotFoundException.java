package com.example.libraryapp.domain.bookitemloan.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class BookItemLoanNotFoundException extends LibraryAppNotFoundException {

    public BookItemLoanNotFoundException(Long bookItemLoanId) {
        super(MessageKey.LOAN_NOT_FOUND_ID, bookItemLoanId.toString());
    }
}
