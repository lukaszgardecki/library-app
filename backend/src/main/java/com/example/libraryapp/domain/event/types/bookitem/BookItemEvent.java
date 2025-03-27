package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.user.model.UserId;
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
