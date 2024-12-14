package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemRequestedEvent;
import org.springframework.context.event.EventListener;

class BookItemEventHandler {

    @EventListener
    public void handleBookItemRequested(BookItemRequestedEvent event) {
        // jeśli książka dostępna:
//        reservation.getBookItem().setStatus(BookItemStatus.RESERVED);
        System.out.println("Obsługuje zdarzenie BookRequestedEvent w BookItemEventHandler");


    }
}
