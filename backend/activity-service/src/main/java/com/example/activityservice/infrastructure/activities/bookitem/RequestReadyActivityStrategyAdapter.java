package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.event.incoming.RequestReadyEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestReadyActivityStrategyAdapter implements ActivityCreationStrategyPort<RequestReadyEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(RequestReadyEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_REQUEST_COMPLETED, event.getBookTitle().value()
        );
        return new Activity(event.getUserId(), ActivityType.REQUEST_COMPLETED, new ActivityMessage(message));
    }

    @Override
    public Class<RequestReadyEvent> supports() {
        return RequestReadyEvent.class;
    }
}