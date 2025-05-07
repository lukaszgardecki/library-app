package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.values.UserId;
import com.example.notificationservice.domain.ports.out.*;
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
        publisher.publishSystemNotificationSentEvent(userId, notification.getSubject());

        userNotificationPreferencesPort.findByUserId(userId).ifPresent(prefs -> {
            if (prefs.getSmsEnabled()) {
                smsNotificationPort.send(notification);
                publisher.publishSmsNotificationSentEvent(userId, notification.getSubject());
            }
            if (prefs.getEmailEnabled()) {
                emailNotificationPort.send(notification);
                publisher.publishEmailNotificationSentEvent(userId, notification.getSubject());
            }
        });
    }
}
