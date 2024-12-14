package com.example.libraryapp.NEWdomain.useractivity.types.bookitem;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemEvent;

public class RequestCancelActivity extends BookItemActivity {

    public RequestCancelActivity(BookItemEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.REQUEST_CANCEL;
        this.message = "Message.ACTION_REQUEST_CANCELLED.getMessage(reservation.getBookItem().getBook().getTitle())";
    }

//    public RequestCancelActivity(Long userId, String bookTitle) {
//        super(userId);
//        this.type = UserActivityType.REQUEST_CANCEL;
//        this.message = "Message.ACTION_REQUEST_CANCELLED.getMessage(reservation.getBookItem().getBook().getTitle())";
//    }
}
