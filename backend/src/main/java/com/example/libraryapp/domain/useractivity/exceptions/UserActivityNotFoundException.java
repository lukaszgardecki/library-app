package com.example.libraryapp.domain.useractivity.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class UserActivityNotFoundException extends LibraryAppNotFoundException {
    public UserActivityNotFoundException(Long userActivityId) {
        super(MessageKey.ACTIVITY_LOGIN_SUCCEEDED, userActivityId.toString());
    }
}
