package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.RequestCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestCreatedActivityStrategyAdapter implements ActivityCreationStrategyPort<RequestCreatedEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(RequestCreatedEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_REQUEST_CREATED, event.getBookTitle()
        );
        return new Activity(new UserId(event.getUserId()), ActivityType.REQUEST_NEW, new ActivityMessage(message));
    }

    @Override
    public Class<RequestCreatedEvent> supports() {
        return RequestCreatedEvent.class;
    }
}