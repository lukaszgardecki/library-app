package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public class BookItemDeletedEvent extends BookItemEvent {

    public BookItemDeletedEvent(BookItemId bookItemId, Title bookTitle) {
        super(bookItemId, new UserId( -1L), bookTitle);
    }
}
