package com.example.libraryapp.domain.librarycard.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.userservice.common.librarycard.model.LibraryCardId;

public class LibraryCardNotFoundException extends LibraryAppNotFoundException {
    public LibraryCardNotFoundException(LibraryCardId libraryCardId) {
        super(MessageKey.CARD_NOT_FOUND_ID, libraryCardId.value().toString());
    }
}
