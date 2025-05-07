package com.example.activityservice.domain.exception;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.model.ActivityId;

public class UserActivityNotFoundException extends LibraryAppNotFoundException {
    public UserActivityNotFoundException(ActivityId activityId) {
        super(MessageKey.ACTIVITY_LOGIN_SUCCEEDED, activityId.value().toString());
    }
}
