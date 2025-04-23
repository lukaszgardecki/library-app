package com.example.userservice.domain.exception;

import com.example.userservice.domain.MessageKey;
import com.example.userservice.domain.exception.LibraryAppException;

public class UnsettledFineException extends LibraryAppException {
    public UnsettledFineException() {
        super(MessageKey.USER_UNSETTLED_CHARGES);
    }
}
