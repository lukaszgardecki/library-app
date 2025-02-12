package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.EmailNotificationPort;
import com.example.libraryapp.domain.notification.ports.NotificationPreferencesRepositoryPort;
import com.example.libraryapp.domain.notification.ports.SmsNotificationPort;
import com.example.libraryapp.domain.notification.ports.SystemNotificationPort;
import com.example.libraryapp.infrastructure.events.event.notification.EmailNotificationSentEvent;
import com.example.libraryapp.infrastructure.events.event.notification.SmsNotificationSentEvent;
import com.example.libraryapp.infrastructure.events.event.notification.SystemNotificationSentEvent;
import com.example.libraryapp.infrastructure.events.publishers.EventPublisherPort;
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
            publisher.publish(new SystemNotificationSentEvent(userId, "wysyłam powiadomienie systemowe"));

            if (prefs.getSmsEnabled()) {
                smsNotificationPort.send(notification);
                publisher.publish(new SmsNotificationSentEvent(userId, "wysyłam powiadomienie sms"));
            }
            if (prefs.getEmailEnabled()) {
                emailNotificationPort.send(notification);
                publisher.publish(new EmailNotificationSentEvent(userId, "wysyłam powiadomienie email"));
            }
        });
    }
}
