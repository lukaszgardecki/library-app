package com.example.activityservice.infrastructure.activities.auth;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.LogoutSuccessEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutSuccessActivityStrategyAdapter implements ActivityCreationStrategy<LogoutSuccessEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(LogoutSuccessEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_LOGOUT, event.getFirstName().value(), event.getLastName().value()
        );
        return new Activity(event.getUserId(), ActivityType.LOGOUT, new ActivityMessage(message));
    }

    @Override
    public Class<LogoutSuccessEvent> supports() {
        return LogoutSuccessEvent.class;
    }
}
