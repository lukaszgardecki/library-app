package com.example.libraryapp.NEWinfrastructure.events.event.bookitem;

import lombok.Getter;

@Getter
public class BookItemRenewedEvent extends BookItemEvent {

    public BookItemRenewedEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
