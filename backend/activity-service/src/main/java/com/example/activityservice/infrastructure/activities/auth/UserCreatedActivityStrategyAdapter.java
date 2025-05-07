package com.example.activityservice.infrastructure.activities.auth;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.UserCreatedEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.MessageProviderPort;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedActivityStrategyAdapter implements ActivityCreationStrategy<UserCreatedEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(UserCreatedEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_REGISTER, event.getFirstName().value(), event.getLastName().value()
        );
        return new Activity(event.getUserId(), ActivityType.REGISTER, new ActivityMessage(message));
    }

    @Override
    public Class<UserCreatedEvent> supports() {
        return UserCreatedEvent.class;
    }
}
