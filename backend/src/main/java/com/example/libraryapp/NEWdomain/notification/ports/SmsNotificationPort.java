package com.example.libraryapp.NEWdomain.notification.ports;

import com.example.libraryapp.NEWdomain.notification.model.Notification;

public interface SmsNotificationPort {
    void send(Notification notification);
}
