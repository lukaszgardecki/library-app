package com.example.notificationservice.infrastructure.notification;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.ports.SmsNotificationPort;
import org.springframework.stereotype.Component;

@Component
class SmsNotificationSenderAdapter implements SmsNotificationPort {
    @Override
    public void send(Notification notification) {
        System.out.println("Wysyłam SMS: " + notification.getContent().value());
    }
}
