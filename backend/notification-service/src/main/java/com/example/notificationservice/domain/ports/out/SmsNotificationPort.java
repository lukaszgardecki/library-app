package com.example.notificationservice.domain.ports.out;

import com.example.notificationservice.domain.model.Notification;

public interface SmsNotificationPort {
    void send(Notification notification);
}
