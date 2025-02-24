package com.example.libraryapp.domain.event.types.bookitem;

import lombok.Getter;

@Getter
public class BookItemRenewalImpossibleEvent extends BookItemEvent {

    public BookItemRenewalImpossibleEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
