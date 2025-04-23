package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.book.Title;
import lombok.Getter;

@Getter
public class BookItemRequestCanceledEvent extends BookItemEvent {

    public BookItemRequestCanceledEvent(BookItemId bookItemId, UserId userId, Title bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
