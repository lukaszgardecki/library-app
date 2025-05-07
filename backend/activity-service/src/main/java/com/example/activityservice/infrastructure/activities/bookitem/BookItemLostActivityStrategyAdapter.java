package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.BookItemLostEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookItemLostActivityStrategyAdapter implements ActivityCreationStrategy<BookItemLostEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(BookItemLostEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_BOOK_ITEM_LOST, event.getBookTitle().value()
        );
        return new Activity(event.getUserId(), ActivityType.BOOK_LOST, new ActivityMessage(message));
    }

    @Override
    public Class<BookItemLostEvent> supports() {
        return BookItemLostEvent.class;
    }
}