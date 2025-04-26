package com.example.notificationservice.infrastructure.notification;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.ports.EmailNotificationPort;
import org.springframework.stereotype.Component;

@Component
class EmailNotificationSenderAdapter implements EmailNotificationPort {
    @Override
    public void send(Notification notification) {
        System.out.println("Wysyłam email: " + notification.getContent().value());
    }
}
