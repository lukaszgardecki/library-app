package com.example.catalogservice.bookitem.domain.event.incoming;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.dto.BookItemRequestDto;
import com.example.catalogservice.bookitem.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemRequestedEvent extends BookItemEvent {
    private final BookItemRequestDto bookItemRequest;

    public BookItemRequestedEvent(BookItemRequestDto bookItemRequest, Title bookTitle) {
        super(new BookItemId(bookItemRequest.getBookItemId()), new UserId(bookItemRequest.getUserId()), bookTitle);
        this.bookItemRequest = bookItemRequest;
    }
}


