package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.EmailNotificationPort;
import com.example.libraryapp.domain.notification.ports.NotificationPreferencesRepositoryPort;
import com.example.libraryapp.domain.notification.ports.SmsNotificationPort;
import com.example.libraryapp.domain.notification.ports.SystemNotificationPort;
import com.example.libraryapp.domain.event.types.notification.EmailNotificationSentEvent;
import com.example.libraryapp.domain.event.types.notification.SmsNotificationSentEvent;
import com.example.libraryapp.domain.event.types.notification.SystemNotificationSentEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationSender {
    private final SmsNotificationPort smsNotificationPort;
    private final EmailNotificationPort emailNotificationPort;
    private final SystemNotificationPort systemNotificationPort;
    private final NotificationPreferencesRepositoryPort userNotificationPreferencesPort;
    private final EventPublisherPort publisher;

    void send(Notification notification) {
        UserId userId = notification.getUserId();
        systemNotificationPort.send(notification);
        publisher.publish(new SystemNotificationSentEvent(userId, notification.getSubject()));

        userNotificationPreferencesPort.findByUserId(userId).ifPresent(prefs -> {
            if (prefs.getSmsEnabled()) {
                smsNotificationPort.send(notification);
                publisher.publish(new SmsNotificationSentEvent(userId, notification.getSubject()));
            }
            if (prefs.getEmailEnabled()) {
                emailNotificationPort.send(notification);
                publisher.publish(new EmailNotificationSentEvent(userId, notification.getSubject()));
            }
        });
    }
}
