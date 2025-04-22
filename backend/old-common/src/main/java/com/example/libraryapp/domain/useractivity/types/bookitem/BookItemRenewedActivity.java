package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityMessage;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookItemRenewedActivity extends BookItemActivity {

    public BookItemRenewedActivity(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_RENEWED;
        this.message = new UserActivityMessage(msgProvider.getMessage(
                MessageKey.ACTIVITY_BOOK_ITEM_RENEWED, event.getBookTitle().value()
        ));
    }
}
