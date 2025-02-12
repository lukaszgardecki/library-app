package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class RenewalImpossibleNotification extends BookItemNotification {

    public RenewalImpossibleNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.RENEWAL_IMPOSSIBLE;
        this.subject = "Message.NOTIFICATION_RENEWAL_IMPOSSIBLE_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_RENEWAL_IMPOSSIBLE_CONTENT.getMessage(lending.getBookItem().getBook().getTitle())";
    }

//    public RenewalImpossibleNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.RENEWAL_IMPOSSIBLE;
//        this.subject = "Message.NOTIFICATION_RENEWAL_IMPOSSIBLE_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_RENEWAL_IMPOSSIBLE_CONTENT.getMessage(lending.getBookItem().getBook().getTitle())";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
