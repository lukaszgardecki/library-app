package com.example.requestservice.domain.event.outgoing;

import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemRequestedEvent {
    private final BookItemId bookItemId;
    private final UserId userId;

    public BookItemRequestedEvent(BookItemRequestDto bookItemRequest) {
        this.bookItemId = new BookItemId(bookItemRequest.getBookItemId());
        this.userId = new UserId(bookItemRequest.getUserId());
    }
}


