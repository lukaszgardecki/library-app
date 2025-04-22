package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitem.model.Price;
import com.example.libraryapp.domain.event.types.bookitem.BookItemLostEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.NotificationContent;
import com.example.libraryapp.domain.notification.model.NotificationSubject;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

import java.math.BigDecimal;

public class BookItemLostNotification extends BookItemNotification {

    public BookItemLostNotification(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        Price charge = ((BookItemLostEvent) event).getCharge();
        this.type = NotificationType.BOOK_LOST;
        this.subject = new NotificationSubject(msgProvider.getMessage(MessageKey.NOTIFICATION_BOOK_LOST_SUBJECT));
        this.content = new NotificationContent(
                msgProvider.getMessage(
                        MessageKey.NOTIFICATION_BOOK_LOST_CONTENT,
                        event.getBookTitle().value(),
                        event.getBookItemId().value(),
                        charge
                )
        );
    }
}
