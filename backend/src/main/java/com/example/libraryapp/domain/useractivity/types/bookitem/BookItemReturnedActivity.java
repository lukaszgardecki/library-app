package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;

public class BookItemReturnedActivity extends BookItemActivity {

    public BookItemReturnedActivity(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_RETURNED;
        this.message = msgProvider.getMessage(
                MessageKey.ACTIVITY_BOOK_ITEM_RETURNED, event.getBookTitle()
        );
    }
}
