package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityMessage;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class RequestCancelActivity extends BookItemActivity {

    public RequestCancelActivity(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = UserActivityType.REQUEST_CANCEL;
        this.message = new UserActivityMessage(msgProvider.getMessage(
                MessageKey.ACTIVITY_REQUEST_CANCELLED, event.getBookTitle().value()
        ));
    }
}
