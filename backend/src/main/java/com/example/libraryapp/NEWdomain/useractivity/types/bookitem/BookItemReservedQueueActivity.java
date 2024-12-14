package com.example.libraryapp.NEWdomain.useractivity.types.bookitem;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemEvent;

public class BookItemReservedQueueActivity extends BookItemActivity {

    public BookItemReservedQueueActivity(BookItemEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_RESERVED_QUEUE;
        this.message = "Message.ACTION_BOOK_RESERVED_QUEUE.getMessage(bookTitle, queuePosition)";
    }

//    public BookItemReservedQueueActivity(Long userId, String bookTitle, int queuePosition) {
//        super(userId);
//        this.type = UserActivityType.BOOK_RESERVED_QUEUE;
//        this.message = "Message.ACTION_BOOK_RESERVED_QUEUE.getMessage(bookTitle, queuePosition)";
//    }
}
