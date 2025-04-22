package com.example.catalogservice.bookitem.domain.event.outgoing;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.event.CustomEvent;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.UserId;
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
