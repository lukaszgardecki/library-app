package com.example.libraryapp.NEWinfrastructure.notification.sender;

import com.example.libraryapp.NEWdomain.notification.model.Notification;
import com.example.libraryapp.NEWdomain.notification.ports.SmsNotificationPort;
import org.springframework.stereotype.Component;

@Component
class SmsNotificationSenderImpl implements SmsNotificationPort {
    @Override
    public void send(Notification notification) {
        System.out.println("Wysyłam SMS: " + notification.getContent());
    }
}
