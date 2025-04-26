package com.example.notificationservice.infrastructure.events;

import com.example.notificationservice.domain.event.outgoing.EmailNotificationSentEvent;
import com.example.notificationservice.domain.event.outgoing.SmsNotificationSentEvent;
import com.example.notificationservice.domain.event.outgoing.SystemNotificationSentEvent;
import com.example.notificationservice.domain.model.NotificationSubject;
import com.example.notificationservice.domain.model.UserId;
import com.example.notificationservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String SYSTEM_NOTIFICATION_SENT_TOPIC = "notification-service.notification.system.sent";
    private static final String SYSTEM_SMS_SENT_TOPIC = "notification-service.notification.sms.sent";
    private static final String SYSTEM_EMAIL_SENT_TOPIC = "notification-service.notification.email.sent";

    @Override
    public void publishSystemNotificationSentEvent(UserId userId, NotificationSubject subject) {
        template.send(SYSTEM_NOTIFICATION_SENT_TOPIC, new SystemNotificationSentEvent(userId, subject));
    }

    @Override
    public void publishSmsNotificationSentEvent(UserId userId, NotificationSubject subject) {
        template.send(SYSTEM_SMS_SENT_TOPIC, new SmsNotificationSentEvent(userId, subject));
    }

    @Override
    public void publishEmailNotificationSentEvent(UserId userId, NotificationSubject subject) {
        template.send(SYSTEM_EMAIL_SENT_TOPIC, new EmailNotificationSentEvent(userId, subject));
    }
}
