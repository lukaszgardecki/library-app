package com.example.libraryapp.NEWdomain.notification.types.bookitem;

import com.example.libraryapp.NEWdomain.notification.model.NotificationType;
import com.example.libraryapp.NEWdomain.notification.types.bookitem.BookItemNotification;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemEvent;

public class BookReservedQueueNotification extends BookItemNotification {

    public BookReservedQueueNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.BOOK_RESERVED_QUEUE;
        this.subject = "Message.NOTIFICATION_BOOK_RESERVED_SUBJECT.getMessage()";
        this.content = queuePosition == 2
                ? "Message.NOTIFICATION_BOOK_RESERVED_CONTENT_QUEUE_1_AHEAD.getMessage(reservation.getBookItem().getBook().getTitle())"
                : "Message.NOTIFICATION_BOOK_RESERVED_CONTENT_QUEUE_2_AHEAD.getMessage(reservation.getBookItem().getBook().getTitle(), queuePosition - 1)";
    }

//    public BookReservedQueueNotification(Long userId, Long bookId, String bookTitle, int queuePosition) {
//        super(userId);
//        this.type = NotificationType.BOOK_RESERVED_QUEUE;
//        this.subject = "Message.NOTIFICATION_BOOK_RESERVED_SUBJECT.getMessage()";
//        this.content = queuePosition == 2
//                ? "Message.NOTIFICATION_BOOK_RESERVED_CONTENT_QUEUE_1_AHEAD.getMessage(reservation.getBookItem().getBook().getTitle())"
//                : "Message.NOTIFICATION_BOOK_RESERVED_CONTENT_QUEUE_2_AHEAD.getMessage(reservation.getBookItem().getBook().getTitle(), queuePosition - 1)";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
