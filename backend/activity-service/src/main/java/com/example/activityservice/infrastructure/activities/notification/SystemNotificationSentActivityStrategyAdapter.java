package com.example.activityservice.infrastructure.activities.notification;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.SystemNotificationSentEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemNotificationSentActivityStrategyAdapter implements ActivityCreationStrategy<SystemNotificationSentEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(SystemNotificationSentEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_NOTIFICATION_SENT_SYSTEM, event.getNotificationSubject().value()
        );
        return new Activity(event.getUserId(), ActivityType.NOTIFICATION_SYSTEM, new ActivityMessage(message));
    }

    @Override
    public Class<SystemNotificationSentEvent> supports() {
        return SystemNotificationSentEvent.class;
    }
}