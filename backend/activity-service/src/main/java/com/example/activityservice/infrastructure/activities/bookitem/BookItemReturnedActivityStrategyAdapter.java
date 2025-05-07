package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.BookItemReturnedEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookItemReturnedActivityStrategyAdapter implements ActivityCreationStrategy<BookItemReturnedEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(BookItemReturnedEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_BOOK_ITEM_RETURNED, event.getBookTitle().value()
        );
        return new Activity(event.getUserId(), ActivityType.BOOK_RETURNED, new ActivityMessage(message));
    }

    @Override
    public Class<BookItemReturnedEvent> supports() {
        return BookItemReturnedEvent.class;
    }
}