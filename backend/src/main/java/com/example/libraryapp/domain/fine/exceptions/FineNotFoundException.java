package com.example.libraryapp.domain.fine.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class FineNotFoundException extends LibraryAppNotFoundException {

    public FineNotFoundException(Long fineId) {
        super(MessageKey.FINE_NOT_FOUND_ID, fineId.toString());
    }
}
