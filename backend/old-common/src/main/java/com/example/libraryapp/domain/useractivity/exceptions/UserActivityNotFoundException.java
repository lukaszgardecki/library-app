package com.example.libraryapp.domain.useractivity.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.libraryapp.domain.useractivity.model.UserActivityId;

public class UserActivityNotFoundException extends LibraryAppNotFoundException {
    public UserActivityNotFoundException(UserActivityId userActivityId) {
        super(MessageKey.ACTIVITY_LOGIN_SUCCEEDED, userActivityId.value().toString());
    }
}
