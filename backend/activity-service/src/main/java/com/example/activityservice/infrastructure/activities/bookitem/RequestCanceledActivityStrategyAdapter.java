package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.event.incoming.RequestCanceledEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestCanceledActivityStrategyAdapter implements ActivityCreationStrategyPort<RequestCanceledEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(RequestCanceledEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_REQUEST_CANCELLED, event.getBookTitle().value()
        );
        return new Activity(event.getUserId(), ActivityType.REQUEST_CANCEL, new ActivityMessage(message));
    }

    @Override
    public Class<RequestCanceledEvent> supports() {
        return RequestCanceledEvent.class;
    }
}