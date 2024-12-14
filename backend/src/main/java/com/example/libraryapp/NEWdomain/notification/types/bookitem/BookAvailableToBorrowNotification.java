package com.example.libraryapp.NEWdomain.notification.types.bookitem;

import com.example.libraryapp.NEWdomain.notification.model.NotificationType;
import com.example.libraryapp.NEWdomain.notification.types.bookitem.BookItemNotification;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemEvent;

public class BookAvailableToBorrowNotification extends BookItemNotification {

    public BookAvailableToBorrowNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.BOOK_AVAILABLE_TO_BORROW;
        this.subject = "Message.NOTIFICATION_BOOK_AVAILABLE_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_BOOK_AVAILABLE_CONTENT.getMessage(reservation.getBookItem().getBook().getTitle())";
    }

//    public BookAvailableToBorrowNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.BOOK_AVAILABLE_TO_BORROW;
//        this.subject = "Message.NOTIFICATION_BOOK_AVAILABLE_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_BOOK_AVAILABLE_CONTENT.getMessage(reservation.getBookItem().getBook().getTitle())";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
