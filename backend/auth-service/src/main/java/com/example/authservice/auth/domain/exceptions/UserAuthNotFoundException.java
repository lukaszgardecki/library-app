package com.example.authservice.auth.domain.exceptions;

import com.example.authservice.common.MessageKey;
import com.example.authservice.common.exception.LibraryAppNotFoundException;

public class UserAuthNotFoundException extends LibraryAppNotFoundException {

    public UserAuthNotFoundException() {
        super(MessageKey.USER_AUTH_NOT_FOUND, "null");
    }
}
