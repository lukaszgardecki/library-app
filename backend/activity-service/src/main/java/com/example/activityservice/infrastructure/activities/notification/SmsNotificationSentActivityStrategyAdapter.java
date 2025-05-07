package com.example.activityservice.infrastructure.activities.notification;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.SmsNotificationSentEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsNotificationSentActivityStrategyAdapter implements ActivityCreationStrategy<SmsNotificationSentEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(SmsNotificationSentEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_NOTIFICATION_SENT_SMS, event.getNotificationSubject().value()
        );
        return new Activity(event.getUserId(), ActivityType.NOTIFICATION_SMS, new ActivityMessage(message));
    }

    @Override
    public Class<SmsNotificationSentEvent> supports() {
        return SmsNotificationSentEvent.class;
    }
}