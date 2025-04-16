package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public class BookItemRequestReadyEvent extends BookItemEvent {

    public BookItemRequestReadyEvent(BookItemId bookItemId, UserId userId, Title bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
