package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;
import com.example.libraryapp.domain.event.types.bookitem.BookItemReservedEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.NotificationType;

import java.time.LocalDate;

public class BookItemReservedNotification extends BookItemNotification {

    public BookItemReservedNotification(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        int queuePosition = ((BookItemReservedEvent) event).getQueue();
        LocalDate dueDate = ((BookItemReservedEvent) event).getDueDate();
        this.type = NotificationType.BOOK_RESERVED_FIRST_PERSON;
        this.subject = msgProvider.getMessage(MessageKey.NOTIFICATION_BOOK_RESERVED_SUBJECT);
        this.content = msgProvider.getMessage(
                MessageKey.NOTIFICATION_BOOK_RESERVED_CONTENT_FIRST_PERSON,
                event.getBookTitle(), event.getBookItemId(), queuePosition, dueDate
        );
    }
}
