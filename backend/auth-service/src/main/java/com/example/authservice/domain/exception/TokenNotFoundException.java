package com.example.authservice.domain.exception;

import com.example.authservice.domain.MessageKey;
import com.example.authservice.domain.exception.LibraryAppNotFoundException;

public class TokenNotFoundException extends LibraryAppNotFoundException {

    public TokenNotFoundException(String hash) {
        super(MessageKey.TOKEN_NOT_FOUND_HASH, hash);
    }

}
