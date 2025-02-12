package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookReservedFirstPersonNotification extends BookItemNotification {

    public BookReservedFirstPersonNotification(BookItemEvent event) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.BOOK_RESERVED_FIRST_PERSON;
        this.subject = "Message.NOTIFICATION_BOOK_RESERVED_SUBJECT.getMessage()";
        this.content = "Message.NOTIFICATION_BOOK_RESERVED_CONTENT_FIRST_PERSON.getMessage(currentLending.getBookItem().getBook().getTitle(), currentLending.getDueDate())";
    }

//    public BookReservedFirstPersonNotification(Long userId, Long bookId, String bookTitle) {
//        super(userId);
//        this.type = NotificationType.BOOK_RESERVED_FIRST_PERSON;
//        this.subject = "Message.NOTIFICATION_BOOK_RESERVED_SUBJECT.getMessage()";
//        this.content = "Message.NOTIFICATION_BOOK_RESERVED_CONTENT_FIRST_PERSON.getMessage(currentLending.getBookItem().getBook().getTitle(), currentLending.getDueDate())";
//        this.bookItemId = bookId;
//        this.bookTitle = bookTitle;
//    }
}
