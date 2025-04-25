package com.example.fineservice.domain.exceptions;

import com.example.fineservice.domain.MessageKey;

public class FineAlreadyPaidException extends LibraryAppException {

    public FineAlreadyPaidException() {
        super(MessageKey.FINE_ALREADY_PAID);
    }
}
