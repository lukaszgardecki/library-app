package com.example.libraryapp.NEWinfrastructure.events.event.bookitem;

import lombok.Getter;

@Getter
public class BookItemLoanedEvent extends BookItemEvent {

    public BookItemLoanedEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}

