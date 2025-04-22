package com.example.libraryapp.domain.notification.ports;

import com.example.libraryapp.domain.notification.model.Notification;

public interface SmsNotificationPort {
    void send(Notification notification);
}
