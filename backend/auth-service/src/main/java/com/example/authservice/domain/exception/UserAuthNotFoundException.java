package com.example.authservice.domain.exception;

import com.example.authservice.domain.MessageKey;

public class UserAuthNotFoundException extends LibraryAppNotFoundException {

    public UserAuthNotFoundException() {
        super(MessageKey.USER_AUTH_NOT_FOUND, "null");
    }
}
