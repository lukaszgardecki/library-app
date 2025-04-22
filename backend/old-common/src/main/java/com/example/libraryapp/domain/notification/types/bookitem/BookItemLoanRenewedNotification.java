package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.event.types.bookitem.BookItemRenewedEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.NotificationContent;
import com.example.libraryapp.domain.notification.model.NotificationSubject;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

import java.time.LocalDateTime;

public class BookItemLoanRenewedNotification extends BookItemNotification {

    public BookItemLoanRenewedNotification(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        LoanDueDate dueDate = ((BookItemRenewedEvent) event).getDueDate();
        this.type = NotificationType.BOOK_RENEWED;
        this.subject = new NotificationSubject(msgProvider.getMessage(MessageKey.NOTIFICATION_BOOK_RENEWED_SUBJECT));
        this.content = new NotificationContent(
                msgProvider.getMessage(
                        MessageKey.NOTIFICATION_BOOK_RENEWED_CONTENT,
                        event.getBookTitle().value(),
                        event.getBookItemId().value(),
                        dueDate
                )
        );
    }
}
