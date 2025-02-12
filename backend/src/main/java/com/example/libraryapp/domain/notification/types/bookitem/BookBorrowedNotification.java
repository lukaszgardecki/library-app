package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookBorrowedNotification extends BookItemNotification {

    public BookBorrowedNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.BOOK_BORROWED;
        this.subject = "Message.NOTIFICATION_BOOK_BORROWED_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_BOOK_BORROWED_CONTENT.getMessage()";
    }

//    public BookBorrowedNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.BOOK_BORROWED;
//        this.subject = "Message.NOTIFICATION_BOOK_BORROWED_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_BOOK_BORROWED_CONTENT.getMessage()";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
