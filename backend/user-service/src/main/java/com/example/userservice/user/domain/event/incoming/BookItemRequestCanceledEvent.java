package com.example.userservice.user.domain.event.incoming;

import com.example.userservice.user.domain.model.bookitem.BookItemId;
import com.example.userservice.user.domain.model.book.Title;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class BookItemRequestCanceledEvent extends BookItemEvent {

    public BookItemRequestCanceledEvent(BookItemId bookItemId, UserId userId, Title bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
