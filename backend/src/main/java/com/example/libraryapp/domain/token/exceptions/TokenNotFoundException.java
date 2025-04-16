package com.example.libraryapp.domain.token.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class TokenNotFoundException extends LibraryAppNotFoundException {

    public TokenNotFoundException(String hash) {
        super(MessageKey.TOKEN_NOT_FOUND_HASH, hash);
    }

}
