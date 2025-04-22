package com.example.libraryapp.domain.user.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class UnsettledFineException extends LibraryAppException {
    public UnsettledFineException() {
        super(MessageKey.USER_UNSETTLED_CHARGES);
    }
}
