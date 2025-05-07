package com.example.activityservice.infrastructure.activities.notification;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.EmailNotificationSentEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationSentActivityStrategyAdapter implements ActivityCreationStrategy<EmailNotificationSentEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(EmailNotificationSentEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_NOTIFICATION_SENT_EMAIL, event.getNotificationSubject().value()
        );
        return new Activity(event.getUserId(), ActivityType.NOTIFICATION_EMAIL, new ActivityMessage(message));
    }

    @Override
    public Class<EmailNotificationSentEvent> supports() {
        return EmailNotificationSentEvent.class;
    }
}