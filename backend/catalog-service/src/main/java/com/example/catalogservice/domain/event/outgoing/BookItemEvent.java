package com.example.catalogservice.domain.event.outgoing;

import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.event.CustomEvent;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.UserId;
import lombok.Getter;

@Getter
public abstract class BookItemEvent extends CustomEvent {
    protected BookItemId bookItemId;
    protected Title bookTitle;

    protected BookItemEvent(BookItemId bookItemId, UserId userId, Title bookTitle) {
        super(userId);
        this.bookItemId = bookItemId;
        this.bookTitle = bookTitle;
    }
}
