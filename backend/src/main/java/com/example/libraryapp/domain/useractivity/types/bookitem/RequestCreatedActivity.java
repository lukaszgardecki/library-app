package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;

public class RequestCreatedActivity extends BookItemActivity {

    public RequestCreatedActivity(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = UserActivityType.REQUEST_NEW;
        this.message = msgProvider.getMessage(
                MessageKey.ACTIVITY_REQUEST_CREATED, event.getBookTitle()
        );
    }
}
