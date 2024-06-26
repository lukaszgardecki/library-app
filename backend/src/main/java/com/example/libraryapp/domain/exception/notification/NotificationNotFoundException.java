package com.example.libraryapp.domain.exception.notification;

import com.example.libraryapp.management.Message;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(Long id) {
        super(String.format(Message.NOTIFICATION_NOT_FOUND, id));
    }
}
