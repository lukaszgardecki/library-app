package com.example.libraryapp.domain.bookitemloan.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitemloan.model.LoanId;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class BookItemLoanNotFoundException extends LibraryAppNotFoundException {

    public BookItemLoanNotFoundException(LoanId bookItemLoanId) {
        super(MessageKey.LOAN_NOT_FOUND_ID, bookItemLoanId.value().toString());
    }

    public BookItemLoanNotFoundException() {
        super(MessageKey.LOAN_NOT_FOUND, "");
    }
}
