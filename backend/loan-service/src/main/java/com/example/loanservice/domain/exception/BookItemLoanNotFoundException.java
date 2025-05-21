package com.example.loanservice.domain.exception;

import com.example.loanservice.domain.i18n.MessageKey;
import com.example.loanservice.domain.model.values.LoanId;

public class BookItemLoanNotFoundException extends LibraryAppNotFoundException {

    public BookItemLoanNotFoundException(LoanId bookItemLoanId) {
        super(MessageKey.LOAN_NOT_FOUND_ID, bookItemLoanId.value().toString());
    }

    public BookItemLoanNotFoundException() {
        super(MessageKey.LOAN_NOT_FOUND, "");
    }
}
