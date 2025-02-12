package com.example.libraryapp.domain.notification.ports;

import com.example.libraryapp.domain.notification.model.Notification;

public interface EmailNotificationPort {
    void send(Notification notification);
}
