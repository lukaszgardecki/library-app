package com.example.activityservice.infrastructure.activities.auth;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.LoginSuccessEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSuccessActivityStrategyAdapter implements ActivityCreationStrategy<LoginSuccessEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(LoginSuccessEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_LOGIN_SUCCEEDED, event.getFirstName().value(), event.getLastName().value()
        );
        return new Activity(event.getUserId(), ActivityType.LOGIN, new ActivityMessage(message));
    }

    @Override
    public Class<LoginSuccessEvent> supports() {
        return LoginSuccessEvent.class;
    }
}
