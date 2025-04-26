package com.example.notificationservice.domain.ports;

import com.example.notificationservice.domain.model.NotificationSubject;
import com.example.notificationservice.domain.model.UserId;

public interface EventPublisherPort {
    void publishSystemNotificationSentEvent(UserId userId, NotificationSubject subject);

    void publishSmsNotificationSentEvent(UserId userId, NotificationSubject subject);

    void publishEmailNotificationSentEvent(UserId userId, NotificationSubject subject);
}
