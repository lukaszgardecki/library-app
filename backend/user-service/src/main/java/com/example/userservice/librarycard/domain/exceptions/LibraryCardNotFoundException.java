package com.example.userservice.librarycard.domain.exceptions;

import com.example.userservice.common.MessageKey;
import com.example.userservice.common.exception.LibraryAppNotFoundException;
import com.example.userservice.librarycard.domain.model.LibraryCardId;

public class LibraryCardNotFoundException extends LibraryAppNotFoundException {
    public LibraryCardNotFoundException(LibraryCardId libraryCardId) {
        super(MessageKey.CARD_NOT_FOUND_ID, libraryCardId.value().toString());
    }
}
