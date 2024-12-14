package com.example.libraryapp.NEWdomain.useractivity.types.bookitem;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemEvent;

public class RequestCompletedActivity extends BookItemActivity {

    public RequestCompletedActivity(BookItemEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.REQUEST_COMPLETED;
        this.message = "Message.ACTION_REQUEST_COMPLETED.getMessage(reservation.getBookItem().getBook().getTitle())";
    }

//    public RequestCompletedActivity(Long userId, String bookTitle) {
//        super(userId);
//        this.type = UserActivityType.REQUEST_COMPLETED;
//        this.message = "Message.ACTION_REQUEST_COMPLETED.getMessage(reservation.getBookItem().getBook().getTitle())";
//    }
}
