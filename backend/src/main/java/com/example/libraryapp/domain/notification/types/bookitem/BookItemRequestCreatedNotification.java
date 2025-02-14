package com.example.libraryapp.domain.notification.types.bookitem;


import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookItemRequestCreatedNotification extends BookItemNotification {

    public BookItemRequestCreatedNotification(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.REQUEST_CREATED;
        this.subject = msgProvider.getMessage(MessageKey.NOTIFICATION_REQUEST_CREATED_SUBJECT);
        this.content = msgProvider.getMessage(
                MessageKey.NOTIFICATION_REQUEST_CREATED_CONTENT, event.getBookTitle(), event.getBookItemId()
        );
    }
}
