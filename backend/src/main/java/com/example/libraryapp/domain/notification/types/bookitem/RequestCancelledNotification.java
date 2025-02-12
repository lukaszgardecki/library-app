package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.infrastructure.events.event.bookitem.BookItemEvent;

public class RequestCancelledNotification extends BookItemNotification {

    public RequestCancelledNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.REQUEST_CANCELLED;
        this.subject = "Message.NOTIFICATION_REQUEST_CANCELLED_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_REQUEST_CANCELLED_CONTENT.getMessage()";
    }

//    public RequestCancelledNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.REQUEST_CANCELLED;
//        this.subject = "Message.NOTIFICATION_REQUEST_CANCELLED_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_REQUEST_CANCELLED_CONTENT.getMessage()";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
