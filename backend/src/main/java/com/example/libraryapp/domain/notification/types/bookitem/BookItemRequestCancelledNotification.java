package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookItemRequestCancelledNotification extends BookItemNotification {

    public BookItemRequestCancelledNotification(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.REQUEST_CANCELLED;
        this.subject = msgProvider.getMessage(MessageKey.NOTIFICATION_REQUEST_CANCELLED_SUBJECT);
        this.content = msgProvider.getMessage(
                MessageKey.NOTIFICATION_REQUEST_CANCELLED_CONTENT, event.getBookTitle(), event.getBookItemId()
        );
    }
}
