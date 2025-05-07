package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.ReservationCreatedEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedActivityStrategyAdapter implements ActivityCreationStrategy<ReservationCreatedEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(ReservationCreatedEvent event) {
        int queue = event.getQueue();
        String message = queue == 1
                ? msgProvider.getMessage(
                        MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_FIRST_PERSON,
                        event.getLoanDueDate().value(),
                        event.getBookTitle().value())
                : msgProvider.getMessage(
                        MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_QUEUE,
                        event.getBookTitle().value(),
                        queue);
        ActivityType type = queue == 1 ? ActivityType.BOOK_RESERVED_FIRST : ActivityType.BOOK_RESERVED_QUEUE;
        return new Activity(event.getUserId(), type, new ActivityMessage(message));
    }

    @Override
    public Class<ReservationCreatedEvent> supports() {
        return ReservationCreatedEvent.class;
    }
}