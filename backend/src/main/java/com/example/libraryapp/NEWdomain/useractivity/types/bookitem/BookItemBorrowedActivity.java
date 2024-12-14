package com.example.libraryapp.NEWdomain.useractivity.types.bookitem;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWdomain.useractivity.types.bookitem.BookItemActivity;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserEvent;

public class BookItemBorrowedActivity extends BookItemActivity {

    public BookItemBorrowedActivity(BookItemEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_BORROWED;
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
