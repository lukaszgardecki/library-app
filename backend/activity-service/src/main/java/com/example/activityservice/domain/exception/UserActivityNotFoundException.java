package com.example.activityservice.domain.exception;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.values.ActivityId;

public class UserActivityNotFoundException extends LibraryAppNotFoundException {
    public UserActivityNotFoundException(ActivityId activityId) {
        super(MessageKey.ACTIVITY_NOT_FOUND_ID, activityId.value().toString());
    }
}
