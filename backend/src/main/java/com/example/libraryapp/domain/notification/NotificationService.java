package com.example.libraryapp.domain.notification;

import com.example.libraryapp.domain.action.ActionRepository;
import com.example.libraryapp.domain.action.types.NotificationSentEmailAction;
import com.example.libraryapp.domain.action.types.NotificationSentSmsAction;
import com.example.libraryapp.domain.notification.types.EmailNotification;
import com.example.libraryapp.domain.notification.types.SMSNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final ActionRepository actionRepository;

    public void sendNotification(NotificationDetails details) {
        sendEmail(details);
        if (details.getMemberPhoneNumber() != null && !details.getMemberPhoneNumber().isBlank()) {
            sendSMS(details);
        }
    }

    private void sendEmail(NotificationDetails details) {
        EmailNotification emailNotification = NotificationFactory.createEmailNotification(details);
        emailNotification.send();
        actionRepository.save(new NotificationSentEmailAction(details));
    }

    private void sendSMS(NotificationDetails details) {
        SMSNotification smsNotificationN = NotificationFactory.createSMSNotification(details);
        smsNotificationN.send();
        actionRepository.save(new NotificationSentSmsAction(details));
    }
}
