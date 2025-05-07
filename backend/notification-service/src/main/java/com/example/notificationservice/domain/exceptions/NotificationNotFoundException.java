package com.example.notificationservice.domain.exceptions;

import com.example.notificationservice.domain.i18n.MessageKey;
import com.example.notificationservice.domain.model.values.NotificationId;

public class NotificationNotFoundException extends LibraryAppNotFoundException {

    public NotificationNotFoundException(NotificationId notificationId) {
        super(MessageKey.NOTIFICATION_NOT_FOUND_ID, notificationId.value().toString());
    }
}
