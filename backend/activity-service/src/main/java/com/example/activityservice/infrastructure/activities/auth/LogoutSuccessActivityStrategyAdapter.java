package com.example.activityservice.infrastructure.activities.auth;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.LogoutSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutSuccessActivityStrategyAdapter implements ActivityCreationStrategyPort<LogoutSuccessEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(LogoutSuccessEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_LOGOUT, event.getFirstName(), event.getLastName()
        );
        return new Activity(new UserId(event.getUserId()), ActivityType.LOGOUT, new ActivityMessage(message));
    }

    @Override
    public Class<LogoutSuccessEvent> supports() {
        return LogoutSuccessEvent.class;
    }
}
