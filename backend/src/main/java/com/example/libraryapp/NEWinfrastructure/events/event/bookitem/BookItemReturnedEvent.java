package com.example.libraryapp.NEWinfrastructure.events.event.bookitem;

import lombok.Getter;

@Getter
public class BookItemReturnedEvent extends BookItemEvent {

    public BookItemReturnedEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
