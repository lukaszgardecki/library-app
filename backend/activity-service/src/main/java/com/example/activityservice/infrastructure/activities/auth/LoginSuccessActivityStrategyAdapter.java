package com.example.activityservice.infrastructure.activities.auth;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.event.incoming.LoginSuccessEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSuccessActivityStrategyAdapter implements ActivityCreationStrategyPort<LoginSuccessEvent> {
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
