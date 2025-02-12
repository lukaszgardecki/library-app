package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.event.types.CustomEvent;
import lombok.Getter;

@Getter
public abstract class BookItemEvent extends CustomEvent {
    protected Long bookItemId;
    protected String bookTitle;

    protected BookItemEvent(Long bookItemId, Long userId, String bookTitle) {
        super(userId);
        this.bookItemId = bookItemId;
        this.bookTitle = bookTitle;
    }
}
