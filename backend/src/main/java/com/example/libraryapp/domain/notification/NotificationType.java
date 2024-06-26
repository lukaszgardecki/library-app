package com.example.libraryapp.domain.notification;

import com.example.libraryapp.management.Message;
import lombok.Getter;

@Getter
public enum NotificationType {
    REQUEST_CREATED(Message.REASON_REQUEST_CREATED, Message.RESERVATION_CREATED),
    REQUEST_CANCELLED(Message.REASON_REQUEST_CANCELED, Message.RESERVATION_DELETED),
    REQUEST_COMPLETED(Message.REASON_REQUEST_COMPLETED, Message.RESERVATION_READY),

    BOOK_BORROWED(Message.REASON_BOOK_BORROWED, Message.BOOK_BORROWED),
    BOOK_EXTENDED(Message.REASON_BOOK_EXTENDED, Message.BOOK_EXTENDED),
    BOOK_RETURNED(Message.REASON_BOOK_RETURNED, Message.BOOK_RETURNED),
    BOOK_LOST(Message.REASON_BOOK_LOST, Message.BOOK_LOST), // do wiadomości trzeba dodać cene książki lub zmienić treść contentu

    RESERVATION_CANCEL_BOOK_ITEM_LOST(Message.REASON_BOOK_LOST, Message.RESERVATION_CANCEL_BOOK_ITEM_LOST)
    ;

    private final String reason;
    private final String content;

    NotificationType(String reason, String content) {
        this.reason = reason;
        this.content = content;
    }
}
