package com.example.loanservice.domain.exception;

import com.example.loanservice.domain.MessageKey;

public class BookItemLoanException extends LibraryAppException {

    public BookItemLoanException(MessageKey messageKey) {
        super(messageKey);
    }
}
