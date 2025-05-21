package com.example.activityservice.infrastructure.activities.auth;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.LoginSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSuccessActivityStrategyAdapter implements ActivityCreationStrategyPort<LoginSuccessEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(LoginSuccessEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_LOGIN_SUCCEEDED, event.getFirstName(), event.getLastName()
        );
        return new Activity(new UserId(event.getUserId()), ActivityType.LOGIN, new ActivityMessage(message));
    }

    @Override
    public Class<LoginSuccessEvent> supports() {
        return LoginSuccessEvent.class;
    }
}
