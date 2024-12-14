package com.example.libraryapp.NEWdomain.notification.exceptions;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(Long id) {
        super("Message.NOTIFICATION_NOT_FOUND.getMessage(id)");
    }
}
