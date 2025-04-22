package com.example.userservice.user.domain.exceptions;

import com.example.userservice.common.MessageKey;
import com.example.userservice.common.exception.LibraryAppException;

public class UnsettledFineException extends LibraryAppException {
    public UnsettledFineException() {
        super(MessageKey.USER_UNSETTLED_CHARGES);
    }
}
