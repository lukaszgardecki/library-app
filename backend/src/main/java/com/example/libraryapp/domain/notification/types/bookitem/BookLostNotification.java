package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookLostNotification extends BookItemNotification {

    public BookLostNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.BOOK_LOST;
        this.subject = "Message.NOTIFICATION_BOOK_LOST_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_BOOK_LOST_CONTENT.getMessage(lending.getBookItem().getPrice())";
    }

//    public BookLostNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.BOOK_LOST;
//        this.subject = "Message.NOTIFICATION_BOOK_LOST_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_BOOK_LOST_CONTENT.getMessage(lending.getBookItem().getPrice())";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
