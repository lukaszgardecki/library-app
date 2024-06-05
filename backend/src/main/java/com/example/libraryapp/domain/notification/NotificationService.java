package com.example.libraryapp.domain.notification;

import com.example.libraryapp.domain.notification.types.EmailNotification;
import com.example.libraryapp.domain.notification.types.SMSNotification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(NotificationDetails details) {
        sendEmail(details);
        if (details.getMemberPhoneNumber() != null && !details.getMemberPhoneNumber().isBlank()) {
            sendSMS(details);
        }
    }

    private void sendEmail(NotificationDetails details) {
        EmailNotification emailNotification = NotificationFactory.createEmailNotification(details);
        emailNotification.send();
    }

    private void sendSMS(NotificationDetails details) {
        SMSNotification smsNotificationN = NotificationFactory.createSMSNotification(details);
        smsNotificationN.send();
    }
}
