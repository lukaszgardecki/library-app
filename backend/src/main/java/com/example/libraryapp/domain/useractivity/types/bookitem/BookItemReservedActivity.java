package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityMessage;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;
import com.example.libraryapp.domain.event.types.bookitem.BookItemReservedEvent;

public class BookItemReservedActivity extends BookItemActivity {

    public BookItemReservedActivity(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        int queue = ((BookItemReservedEvent) event).getQueue();
        this.type = queue == 1 ? UserActivityType.BOOK_RESERVED_FIRST : UserActivityType.BOOK_RESERVED_QUEUE;
        this.message = new UserActivityMessage(
                queue == 1
                        ? msgProvider.getMessage(
                            MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_FIRST_PERSON,
                            event.getBookTitle().value(),
                            event.getBookTitle().value()
                        )
                        : msgProvider.getMessage(
                            MessageKey.ACTIVITY_BOOK_ITEM_RESERVED_QUEUE,
                            event.getBookTitle().value(),
                            queue
                        )
        );
    }
}
