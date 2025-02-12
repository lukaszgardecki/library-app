package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.infrastructure.events.event.bookitem.BookItemLoanedEvent;

public class BookItemBorrowedActivity extends BookItemActivity {
    private boolean isReferenceOnly;

    public BookItemBorrowedActivity(BookItemLoanedEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_BORROWED;
        this.isReferenceOnly = event.isReferenceOnly();
        if (isReferenceOnly) {
            this.message = "Message.ACTION_BOOK_BORROWED_ON_SITE.getMessage(lending.getBookItem().getBook().getTitle())";
        } else {
            this.message = "Message.ACTION_BOOK_BORROWED.getMessage(lending.getBookItem().getBook().getTitle())";
        }
    }

//    public BookItemBorrowedActivity(Long userId, String bookTitle, boolean isReferenceOnly) {
//        super(userId);
//        this.type = UserActivityType.BOOK_BORROWED;
//        if (isReferenceOnly) {
//            this.message = "Message.ACTION_BOOK_BORROWED_ON_SITE.getMessage(lending.getBookItem().getBook().getTitle())";
//        } else {
//            this.message = "Message.ACTION_BOOK_BORROWED.getMessage(lending.getBookItem().getBook().getTitle())";
//        }
//    }
}
