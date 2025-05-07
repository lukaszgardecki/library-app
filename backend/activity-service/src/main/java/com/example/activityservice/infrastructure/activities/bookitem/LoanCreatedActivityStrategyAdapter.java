package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.LoanCreatedEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanCreatedActivityStrategyAdapter implements ActivityCreationStrategy<LoanCreatedEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(LoanCreatedEvent event) {
        String message = event.getIsReferenceOnly()
                ? msgProvider.getMessage(MessageKey.ACTIVITY_BOOK_ITEM_BORROWED_ON_SITE, event.getBookTitle().value())
                : msgProvider.getMessage(MessageKey.ACTIVITY_BOOK_ITEM_BORROWED, event.getBookTitle().value());
        return new Activity(event.getUserId(), ActivityType.BOOK_BORROWED, new ActivityMessage(message));
    }

    @Override
    public Class<LoanCreatedEvent> supports() {
        return LoanCreatedEvent.class;
    }
}