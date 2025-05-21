package com.example.notificationservice.domain.ports.out;

import com.example.notificationservice.domain.model.values.NotificationSubject;
import com.example.notificationservice.domain.model.values.UserId;

public interface EventPublisherPort {
    void publishSystemNotificationSentEvent(UserId userId, NotificationSubject subject);

    void publishSmsNotificationSentEvent(UserId userId, NotificationSubject subject);

    void publishEmailNotificationSentEvent(UserId userId, NotificationSubject subject);
}
