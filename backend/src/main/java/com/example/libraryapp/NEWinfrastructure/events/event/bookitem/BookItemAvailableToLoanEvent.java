package com.example.libraryapp.NEWinfrastructure.events.event.bookitem;

import lombok.Getter;

@Getter
public class BookItemAvailableToLoanEvent extends BookItemEvent {

    public BookItemAvailableToLoanEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
