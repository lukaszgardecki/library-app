package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.RequestReadyEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestReadyActivityStrategyAdapter implements ActivityCreationStrategy<RequestReadyEvent> {
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