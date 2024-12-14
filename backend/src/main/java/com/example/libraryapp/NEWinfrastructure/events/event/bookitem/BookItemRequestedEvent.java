package com.example.libraryapp.NEWinfrastructure.events.event.bookitem;

import lombok.Getter;

@Getter
public class BookItemRequestedEvent extends BookItemEvent {

    public BookItemRequestedEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}


