package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class BookItemReturnedActivity extends BookItemActivity {

    public BookItemReturnedActivity(BookItemEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_RETURNED;
        this.message = "Message.ACTION_BOOK_RETURNED.getMessage(lending.getBookItem().getBook().getTitle())";
    }

//    public BookItemReturnedActivity(Long userId, String bookTitle) {
//        super(userId);
//        this.type = UserActivityType.BOOK_RETURNED;
//        this.message = "Message.ACTION_BOOK_RETURNED.getMessage(lending.getBookItem().getBook().getTitle())";
//    }
}
