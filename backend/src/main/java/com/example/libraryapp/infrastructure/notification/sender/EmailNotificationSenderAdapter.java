package com.example.libraryapp.infrastructure.notification.sender;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.EmailNotificationPort;
import org.springframework.stereotype.Component;

@Component
class EmailNotificationSenderAdapter implements EmailNotificationPort {
    @Override
    public void send(Notification notification) {
        System.out.println("Wysyłam email: " + notification.getContent());
    }
}
