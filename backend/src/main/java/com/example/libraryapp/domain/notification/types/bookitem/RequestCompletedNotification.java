package com.example.libraryapp.domain.notification.types.bookitem;


import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class RequestCompletedNotification extends BookItemNotification {

    public RequestCompletedNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.REQUEST_COMPLETED;
        this.subject = "Message.NOTIFICATION_REQUEST_COMPLETED_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_REQUEST_COMPLETED_CONTENT.getMessage()";
    }

//    public RequestCompletedNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.REQUEST_COMPLETED;
//        this.subject = "Message.NOTIFICATION_REQUEST_COMPLETED_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_REQUEST_COMPLETED_CONTENT.getMessage()";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
