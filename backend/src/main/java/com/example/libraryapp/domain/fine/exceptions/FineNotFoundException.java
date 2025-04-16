package com.example.libraryapp.domain.fine.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.libraryapp.domain.fine.model.FineId;

public class FineNotFoundException extends LibraryAppNotFoundException {

    public FineNotFoundException(FineId fineId) {
        super(MessageKey.FINE_NOT_FOUND_ID, fineId.value().toString());
    }
}
