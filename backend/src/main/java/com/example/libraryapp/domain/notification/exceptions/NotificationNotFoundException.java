package com.example.libraryapp.domain.notification.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.libraryapp.domain.notification.model.NotificationId;

public class NotificationNotFoundException extends LibraryAppNotFoundException {

    public NotificationNotFoundException(NotificationId notificationId) {
        super(MessageKey.NOTIFICATION_NOT_FOUND_ID, notificationId.value().toString());
    }
}
