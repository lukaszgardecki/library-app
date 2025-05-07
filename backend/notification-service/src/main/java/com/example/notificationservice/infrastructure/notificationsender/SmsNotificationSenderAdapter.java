package com.example.notificationservice.infrastructure.notificationsender;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.ports.out.SmsNotificationPort;
import org.springframework.stereotype.Component;

@Component
class SmsNotificationSenderAdapter implements SmsNotificationPort {
    @Override
    public void send(Notification notification) {
        System.out.println("Wysyłam SMS: " + notification.getContent().value());
    }
}
