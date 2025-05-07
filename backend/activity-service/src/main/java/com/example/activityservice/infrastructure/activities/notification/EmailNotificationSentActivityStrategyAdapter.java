package com.example.activityservice.infrastructure.activities.notification;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.event.incoming.EmailNotificationSentEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationSentActivityStrategyAdapter implements ActivityCreationStrategyPort<EmailNotificationSentEvent> {
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