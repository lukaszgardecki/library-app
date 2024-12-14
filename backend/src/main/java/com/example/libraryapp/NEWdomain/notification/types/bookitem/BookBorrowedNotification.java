package com.example.libraryapp.NEWdomain.notification.types.bookitem;

import com.example.libraryapp.NEWdomain.notification.model.NotificationType;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemEvent;

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
