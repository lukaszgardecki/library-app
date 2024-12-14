package com.example.libraryapp.NEWdomain.useractivity.types.bookitem;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemEvent;

public class RequestCreatedActivity extends BookItemActivity {

    public RequestCreatedActivity(BookItemEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.REQUEST_NEW;
        this.message = "Message.ACTION_REQUEST_SENT.getMessage(reservation.getBookItem().getBook().getTitle())";
    }

//    public RequestCreatedActivity(Long userId, String bookTitle) {
//        super(userId);
//        this.type = UserActivityType.REQUEST_NEW;
//        this.message = "Message.ACTION_REQUEST_SENT.getMessage(reservation.getBookItem().getBook().getTitle())";
//    }
}
