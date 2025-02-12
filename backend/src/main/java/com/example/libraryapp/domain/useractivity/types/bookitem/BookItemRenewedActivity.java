package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookItemRenewedActivity extends BookItemActivity {

    public BookItemRenewedActivity(BookItemEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_RENEWED;
        this.message = "Message.ACTION_BOOK_RENEWED.getMessage(lending.getBookItem().getBook().getTitle())";
    }

//    public BookItemRenewedActivity(Long userId, String bookTitle) {
//        super(userId);
//        this.type = UserActivityType.BOOK_RENEWED;
//        this.message = "Message.ACTION_BOOK_RENEWED.getMessage(lending.getBookItem().getBook().getTitle())";
//    }
}
