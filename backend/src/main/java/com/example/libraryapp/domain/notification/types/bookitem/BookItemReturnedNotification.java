package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.NotificationContent;
import com.example.libraryapp.domain.notification.model.NotificationSubject;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookItemReturnedNotification extends BookItemNotification {

    public BookItemReturnedNotification(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.BOOK_RETURNED;
        this.subject = new NotificationSubject(msgProvider.getMessage(MessageKey.NOTIFICATION_BOOK_RETURNED_SUBJECT));
        this.content = new NotificationContent(
                msgProvider.getMessage(
                        MessageKey.NOTIFICATION_BOOK_RETURNED_CONTENT,
                        event.getBookTitle().value(),
                        event.getBookItemId().value()
                )
        );
    }
}
