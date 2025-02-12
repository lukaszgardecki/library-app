package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookRenewedNotification extends BookItemNotification {

    public BookRenewedNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.BOOK_RENEWED;
        this.subject = "Message.NOTIFICATION_BOOK_RENEWED_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_BOOK_RENEWED_CONTENT.getMessage()";
    }

//    public BookRenewedNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.BOOK_RENEWED;
//        this.subject = "Message.NOTIFICATION_BOOK_RENEWED_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_BOOK_RENEWED_CONTENT.getMessage()";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
