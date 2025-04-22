package com.example.authservice.token.domain.exceptions;

import com.example.authservice.common.MessageKey;
import com.example.authservice.common.exception.LibraryAppNotFoundException;

public class TokenNotFoundException extends LibraryAppNotFoundException {

    public TokenNotFoundException(String hash) {
        super(MessageKey.TOKEN_NOT_FOUND_HASH, hash);
    }

}
