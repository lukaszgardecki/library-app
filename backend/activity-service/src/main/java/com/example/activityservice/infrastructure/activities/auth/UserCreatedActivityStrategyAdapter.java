package com.example.activityservice.infrastructure.activities.auth;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.event.incoming.UserCreatedEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedActivityStrategyAdapter implements ActivityCreationStrategyPort<UserCreatedEvent> {
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
