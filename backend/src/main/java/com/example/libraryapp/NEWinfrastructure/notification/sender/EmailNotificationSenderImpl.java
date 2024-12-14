package com.example.libraryapp.NEWinfrastructure.notification.sender;

import com.example.libraryapp.NEWdomain.notification.model.Notification;
import com.example.libraryapp.NEWdomain.notification.ports.EmailNotificationPort;
import org.springframework.stereotype.Component;

@Component
class EmailNotificationSenderImpl implements EmailNotificationPort {
    @Override
    public void send(Notification notification) {
        System.out.println("Wysyłam email: " + notification.getContent());
    }
}
