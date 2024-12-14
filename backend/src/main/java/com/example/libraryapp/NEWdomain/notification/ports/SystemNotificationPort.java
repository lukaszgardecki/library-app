package com.example.libraryapp.NEWdomain.notification.ports;

import com.example.libraryapp.NEWdomain.notification.model.Notification;

public interface SystemNotificationPort {
    void send(Notification notification);
}
