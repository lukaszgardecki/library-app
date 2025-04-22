package com.example.libraryapp.domain.fine.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class FineAlreadyPaidException extends LibraryAppException {

    public FineAlreadyPaidException() {
        super(MessageKey.FINE_ALREADY_PAID);
    }
}
