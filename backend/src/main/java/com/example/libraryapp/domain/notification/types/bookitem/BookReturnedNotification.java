package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.infrastructure.events.event.bookitem.BookItemEvent;

public class BookReturnedNotification extends BookItemNotification {

    public BookReturnedNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.BOOK_RETURNED;
        this.subject = "Message.NOTIFICATION_BOOK_RETURNED_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_BOOK_RETURNED_CONTENT.getMessage()";
    }

//    public BookReturnedNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.BOOK_RETURNED;
//        this.subject = "Message.NOTIFICATION_BOOK_RETURNED_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_BOOK_RETURNED_CONTENT.getMessage()";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
