package com.example.notificationservice.infrastructure.kafka;

import com.example.notificationservice.domain.model.values.NotificationSubject;
import com.example.notificationservice.domain.model.values.UserId;
import com.example.notificationservice.domain.ports.out.EventPublisherPort;
import com.example.notificationservice.infrastructure.kafka.event.outgoing.EmailNotificationSentEvent;
import com.example.notificationservice.infrastructure.kafka.event.outgoing.SmsNotificationSentEvent;
import com.example.notificationservice.infrastructure.kafka.event.outgoing.SystemNotificationSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String SYSTEM_NOTIFICATION_SENT_TOPIC = "notification-service.notification.system.sent";
    private static final String SYSTEM_SMS_SENT_TOPIC = "notification-service.notification.sms.sent";
    private static final String SYSTEM_EMAIL_SENT_TOPIC = "notification-service.notification.email.sent";

    @Override
    public void publishSystemNotificationSentEvent(UserId userId, NotificationSubject subject) {
        template.send(SYSTEM_NOTIFICATION_SENT_TOPIC, new SystemNotificationSentEvent(userId.value(), subject.value()));
    }

    @Override
    public void publishSmsNotificationSentEvent(UserId userId, NotificationSubject subject) {
        template.send(SYSTEM_SMS_SENT_TOPIC, new SmsNotificationSentEvent(userId.value(), subject.value()));
    }

    @Override
    public void publishEmailNotificationSentEvent(UserId userId, NotificationSubject subject) {
        template.send(SYSTEM_EMAIL_SENT_TOPIC, new EmailNotificationSentEvent(userId.value(), subject.value()));
    }
}
