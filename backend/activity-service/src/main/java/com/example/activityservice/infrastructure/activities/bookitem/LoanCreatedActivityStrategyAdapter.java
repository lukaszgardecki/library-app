package com.example.activityservice.infrastructure.activities.bookitem;

import com.example.activityservice.domain.i18n.MessageKey;
import com.example.activityservice.domain.event.incoming.LoanCreatedEvent;
import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityMessage;
import com.example.activityservice.domain.model.values.ActivityType;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.MessageProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanCreatedActivityStrategyAdapter implements ActivityCreationStrategyPort<LoanCreatedEvent> {
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