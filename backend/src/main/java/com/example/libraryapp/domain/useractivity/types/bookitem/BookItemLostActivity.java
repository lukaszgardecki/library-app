package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.infrastructure.events.event.bookitem.BookItemEvent;

public class BookItemLostActivity extends BookItemActivity {

    public BookItemLostActivity(BookItemEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_LOST;
        this.message = "Message.ACTION_BOOK_LOST.getMessage(lending.getBookItem().getBook().getTitle())";
    }

//    public BookItemLostActivity(Long userId, String bookTitle) {
//        super(userId);
//        this.type = UserActivityType.BOOK_LOST;
//        this.message = "Message.ACTION_BOOK_LOST.getMessage(lending.getBookItem().getBook().getTitle())";
//    }
}
