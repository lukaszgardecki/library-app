package com.example.notificationservice.domain.ports.out;

import com.example.notificationservice.domain.model.Notification;

public interface SystemNotificationPort {
    void send(Notification notification);
}
