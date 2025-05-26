package com.example.authservice.domain.exception;

import com.example.authservice.domain.i18n.MessageKey;

public class TokenNotFoundException extends LibraryAppNotFoundException {

    public TokenNotFoundException(String hash) {
        super(MessageKey.TOKEN_NOT_FOUND_HASH, hash);
    }

}
