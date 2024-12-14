package com.example.libraryapp.NEWdomain.notification.ports;

import com.example.libraryapp.NEWdomain.notification.model.Notification;

public interface EmailNotificationPort {
    void send(Notification notification);
}
