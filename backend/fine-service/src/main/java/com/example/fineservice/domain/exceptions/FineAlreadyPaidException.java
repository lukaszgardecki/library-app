package com.example.fineservice.domain.exceptions;

import com.example.fineservice.domain.i18n.MessageKey;

public class FineAlreadyPaidException extends LibraryAppException {

    public FineAlreadyPaidException() {
        super(MessageKey.FINE_ALREADY_PAID);
    }
}
