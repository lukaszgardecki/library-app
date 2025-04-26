package com.example.notificationservice.domain.ports;

import com.example.notificationservice.domain.model.Notification;

public interface SmsNotificationPort {
    void send(Notification notification);
}
