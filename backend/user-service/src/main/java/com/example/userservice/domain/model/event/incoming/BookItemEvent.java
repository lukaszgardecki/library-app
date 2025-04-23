package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.event.CustomEvent;
import com.example.userservice.domain.model.book.Title;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.user.UserId;
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
