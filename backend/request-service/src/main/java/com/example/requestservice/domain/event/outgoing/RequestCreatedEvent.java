package com.example.requestservice.domain.event.outgoing;

import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.model.*;
import lombok.Getter;

@Getter
public class RequestCreatedEvent {
    private final RequestId requestId;
    private final BookItemRequestCreationDate requestCreationDate;
    private final BookItemRequestStatus requestStatus;
    private final BookItemId bookItemId;
    private final UserId userId;
    private final Title bookTitle;

    public RequestCreatedEvent(BookItemRequestDto bookItemRequest, Title bookTitle) {
        this.requestId = new RequestId(bookItemRequest.getId());
        this.requestCreationDate = new BookItemRequestCreationDate(bookItemRequest.getCreationDate());
        this.requestStatus = bookItemRequest.getStatus();
        this.bookItemId = new BookItemId(bookItemRequest.getBookItemId());
        this.userId = new UserId(bookItemRequest.getUserId());
        this.bookTitle = bookTitle;
    }
}


