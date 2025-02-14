package com.example.libraryapp.domain.librarycard.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class LibraryCardNotFoundException extends LibraryAppNotFoundException {
    public LibraryCardNotFoundException(Long libraryCardId) {
        super(MessageKey.CARD_NOT_FOUND_ID, libraryCardId.toString());
    }
}
