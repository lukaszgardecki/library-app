package com.example.loanservice.domain.event.incoming;

import com.example.catalogservice.domain.dto.BookItemRequestDto;
import com.example.catalogservice.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.domain.model.UserId;
import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import lombok.Getter;

@Getter
public class BookItemRequestedEvent extends BookItemEvent {
    private final BookItemRequestDto bookItemRequest;

    public BookItemRequestedEvent(BookItemRequestDto bookItemRequest, Title bookTitle) {
        super(new BookItemId(bookItemRequest.getBookItemId()), new UserId(bookItemRequest.getUserId()), bookTitle);
        this.bookItemRequest = bookItemRequest;
    }
}


