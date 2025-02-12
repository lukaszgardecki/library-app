package com.example.libraryapp.domain.notification.types.bookitem;


import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class ReservationCancelBookItemLostNotification extends BookItemNotification {

    public ReservationCancelBookItemLostNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.RESERVATION_CANCEL_BOOK_ITEM_LOST;
        this.subject = "Message.NOTIFICATION_BOOK_LOST_SUBJECT.getMessage()";
        this.content = "Message.RESERVATION_CANCELLATION_BOOK_ITEM_LOST.getMessage()";
    }

//    public ReservationCancelBookItemLostNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.RESERVATION_CANCEL_BOOK_ITEM_LOST;
//        this.subject = "Message.NOTIFICATION_BOOK_LOST_SUBJECT.getMessage()";
//        this.content = "Message.RESERVATION_CANCELLATION_BOOK_ITEM_LOST.getMessage()";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
