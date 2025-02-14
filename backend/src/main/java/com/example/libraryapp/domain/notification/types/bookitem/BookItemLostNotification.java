package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.event.types.bookitem.BookItemLostEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

import java.math.BigDecimal;

public class BookItemLostNotification extends BookItemNotification {

    public BookItemLostNotification(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        BigDecimal charge = ((BookItemLostEvent) event).getCharge();
        this.type = NotificationType.BOOK_LOST;
        this.subject = msgProvider.getMessage(MessageKey.NOTIFICATION_BOOK_LOST_SUBJECT);
        this.content = msgProvider.getMessage(
                MessageKey.NOTIFICATION_BOOK_LOST_CONTENT, event.getBookTitle(), event.getBookItemId(), charge
        );
    }
}
