package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookItemBorrowedNotification extends BookItemNotification {

    public BookItemBorrowedNotification(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.BOOK_BORROWED;
        this.subject = msgProvider.getMessage(MessageKey.NOTIFICATION_USER_REGISTERED_SUBJECT);
        this.content = msgProvider.getMessage(
                MessageKey.NOTIFICATION_USER_REGISTERED_CONTENT, event.getBookTitle(), event.getBookItemId()
        );
    }
}
