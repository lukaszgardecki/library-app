package com.example.notificationservice.domain.ports.out;

import com.example.notificationservice.domain.model.Notification;

public interface EmailNotificationPort {
    void send(Notification notification);
}
