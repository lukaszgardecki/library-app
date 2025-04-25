package com.example.fineservice.domain.exceptions;

import com.example.fineservice.domain.MessageKey;

public class UnsettledFineException extends LibraryAppException {
    public UnsettledFineException() {
        super(MessageKey.USER_UNSETTLED_CHARGES);
    }
}
