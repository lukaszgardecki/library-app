package com.example.userservice.domain.exception;

import com.example.userservice.domain.i18n.MessageKey;

public class UnsettledFineException extends LibraryAppException {
    public UnsettledFineException() {
        super(MessageKey.USER_UNSETTLED_CHARGES);
    }
}
