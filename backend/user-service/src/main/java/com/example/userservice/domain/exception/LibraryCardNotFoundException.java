package com.example.userservice.domain.exception;

import com.example.userservice.domain.MessageKey;
import com.example.userservice.domain.model.librarycard.LibraryCardId;

public class LibraryCardNotFoundException extends LibraryAppNotFoundException {
    public LibraryCardNotFoundException(LibraryCardId libraryCardId) {
        super(MessageKey.CARD_NOT_FOUND_ID, libraryCardId.value().toString());
    }
}
