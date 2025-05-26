package com.example.activityservice.infrastructure.activities.notification;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.SystemNotificationSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemNotificationSentActivityStrategyAdapter implements ActivityCreationStrategyPort<SystemNotificationSentEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(SystemNotificationSentEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_NOTIFICATION_SENT_SYSTEM, event.getNotificationSubject()
        );
        return new Activity(new UserId(event.getUserId()), ActivityType.NOTIFICATION_SYSTEM, new ActivityMessage(message));
    }

    @Override
    public Class<SystemNotificationSentEvent> supports() {
        return SystemNotificationSentEvent.class;
    }
}