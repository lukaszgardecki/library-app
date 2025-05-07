package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.MessageKey;
import com.example.activityservice.domain.event.incoming.LoanProlongedEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.ActivityMessage;
import com.example.activityservice.domain.model.ActivityType;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanProlongedActivityStrategyAdapter implements ActivityCreationStrategy<LoanProlongedEvent> {
    private final MessageProviderPort msgProvider;

    @Override
    public Activity create(LoanProlongedEvent event) {
        String message = msgProvider.getMessage(
                MessageKey.ACTIVITY_BOOK_ITEM_RENEWED, event.getBookTitle().value()
        );
        return new Activity(event.getUserId(), ActivityType.BOOK_RENEWED, new ActivityMessage(message));
    }

    @Override
    public Class<LoanProlongedEvent> supports() {
        return LoanProlongedEvent.class;
    }
}