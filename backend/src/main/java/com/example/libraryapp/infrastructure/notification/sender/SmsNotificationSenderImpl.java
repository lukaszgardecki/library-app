package com.example.libraryapp.infrastructure.notification.sender;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.SmsNotificationPort;
import org.springframework.stereotype.Component;

@Component
class SmsNotificationSenderImpl implements SmsNotificationPort {
    @Override
    public void send(Notification notification) {
        System.out.println("Wysyłam SMS: " + notification.getContent());
    }
}
