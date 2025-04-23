package com.example.warehouseservice.domain.event.incoming;

import com.example.warehouseservice.domain.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.event.CustomEvent;
import com.example.warehouseservice.domain.model.BookItemId;
import com.example.warehouseservice.domain.model.Title;
import com.example.warehouseservice.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemRequestedEvent extends CustomEvent {
    protected BookItemId bookItemId;
    protected Title bookTitle;
    private final BookItemRequestDto bookItemRequest;

    public BookItemRequestedEvent(BookItemRequestDto bookItemRequest, Title bookTitle) {
        super(new UserId(bookItemRequest.getUserId()));
        this.bookItemId = new BookItemId(bookItemRequest.getBookItemId());
        this.bookTitle = bookTitle;
        this.bookItemRequest = bookItemRequest;
    }
}


