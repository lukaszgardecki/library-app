package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class RequestCompletedActivity extends BookItemActivity {

    public RequestCompletedActivity(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = UserActivityType.REQUEST_COMPLETED;
        this.message = msgProvider.getMessage(
                MessageKey.ACTIVITY_REQUEST_COMPLETED, event.getBookTitle()
        );
    }
}
