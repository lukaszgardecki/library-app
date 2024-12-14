package com.example.libraryapp.NEWapplication.notification;

import com.example.libraryapp.NEWdomain.notification.model.Notification;
import com.example.libraryapp.NEWdomain.notification.ports.EmailNotificationPort;
import com.example.libraryapp.NEWdomain.notification.ports.NotificationPreferencesRepositoryPort;
import com.example.libraryapp.NEWdomain.notification.ports.SmsNotificationPort;
import com.example.libraryapp.NEWdomain.notification.ports.SystemNotificationPort;
import com.example.libraryapp.NEWinfrastructure.events.event.notification.EmailNotificationSentEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.notification.SmsNotificationSentEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.notification.SystemNotificationSentEvent;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationSender {
    private final SmsNotificationPort smsNotificationPort;
    private final EmailNotificationPort emailNotificationPort;
    private final SystemNotificationPort systemNotificationPort;
    private final NotificationPreferencesRepositoryPort userNotificationPreferencesPort;
    private final EventPublisherPort publisher;

    void send(Notification notification) {
        Long userId = notification.getUserId();
        userNotificationPreferencesPort.findByUserId(userId).ifPresent(prefs -> {
            systemNotificationPort.send(notification);
            publisher.publish(new SystemNotificationSentEvent(userId, "jakiś tekst"));

            if (prefs.getSmsEnabled()) {
                smsNotificationPort.send(notification);
                publisher.publish(new SmsNotificationSentEvent(userId, "jakiś tekst"));
            }
            if (prefs.getEmailEnabled()) {
                emailNotificationPort.send(notification);
                publisher.publish(new EmailNotificationSentEvent(userId, "jakiś tekst"));
            }
        });
    }
}
