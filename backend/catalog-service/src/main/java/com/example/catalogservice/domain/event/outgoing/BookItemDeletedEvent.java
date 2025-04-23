package com.example.catalogservice.domain.event.outgoing;

import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.UserId;
import lombok.Getter;

@Getter
public class BookItemDeletedEvent extends BookItemEvent {

    public BookItemDeletedEvent(BookItemId bookItemId, Title bookTitle) {
        super(bookItemId, new UserId( -1L), bookTitle);
    }
}
