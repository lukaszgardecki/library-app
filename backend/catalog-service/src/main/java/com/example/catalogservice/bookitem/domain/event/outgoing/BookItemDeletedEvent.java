package com.example.catalogservice.bookitem.domain.event.outgoing;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemDeletedEvent extends BookItemEvent {

    public BookItemDeletedEvent(BookItemId bookItemId, Title bookTitle) {
        super(bookItemId, new UserId( -1L), bookTitle);
    }
}
