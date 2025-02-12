package com.example.libraryapp.domain.notification.types.bookitem;


import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class RequestCreatedNotification extends BookItemNotification {

    public RequestCreatedNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.REQUEST_CREATED;
        this.subject = "Message.NOTIFICATION_REQUEST_CREATED_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_REQUEST_CREATED_CONTENT.getMessage()";
    }

//    public RequestCreatedNotification(Long userId, Long bookItemId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.REQUEST_CREATED;
//        this.subject = "Message.NOTIFICATION_REQUEST_CREATED_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_REQUEST_CREATED_CONTENT.getMessage()";
//        this.bookItemId = bookItemId;
//        this.bookTitle = bookTitle;
//    }
}
