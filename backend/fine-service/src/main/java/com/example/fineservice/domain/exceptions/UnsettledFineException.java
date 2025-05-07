package com.example.fineservice.domain.exceptions;

import com.example.fineservice.domain.i18n.MessageKey;

public class UnsettledFineException extends LibraryAppException {
    public UnsettledFineException() {
        super(MessageKey.USER_UNSETTLED_CHARGES);
    }
}
