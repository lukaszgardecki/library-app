package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedActivityStrategyAdapter implements ActivityCreationStrategyPort<ReservationCreatedEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(ReservationCreatedEvent event) {
        int queue = event.getQueue();
        String message = queue == 1
                ? msgProvider.getMessage(
                        MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_FIRST_PERSON,
                        event.getLoanDueDate(),
                        event.getBookTitle())
                : msgProvider.getMessage(
                        MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_QUEUE,
                        event.getBookTitle(),
                        queue);
        ActivityType type = queue == 1 ? ActivityType.BOOK_RESERVED_FIRST : ActivityType.BOOK_RESERVED_QUEUE;
        return new Activity(new UserId(event.getUserId()), type, new ActivityMessage(message));
    }

    @Override
    public Class<ReservationCreatedEvent> supports() {
        return ReservationCreatedEvent.class;
    }
}