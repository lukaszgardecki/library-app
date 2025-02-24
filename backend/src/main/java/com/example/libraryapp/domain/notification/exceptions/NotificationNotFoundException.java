package com.example.libraryapp.domain.notification.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class NotificationNotFoundException extends LibraryAppNotFoundException {

    public NotificationNotFoundException(Long notificationId) {
        super(MessageKey.NOTIFICATION_NOT_FOUND_ID, notificationId.toString());
    }
}
