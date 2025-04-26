package com.example.notificationservice.domain.ports;

import com.example.notificationservice.domain.model.Notification;

public interface SystemNotificationPort {
    void send(Notification notification);
}
