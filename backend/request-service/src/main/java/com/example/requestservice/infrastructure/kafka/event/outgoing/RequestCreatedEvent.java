package com.example.requestservice.infrastructure.kafka.event.outgoing;

import com.example.requestservice.infrastructure.http.dto.BookItemRequestDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RequestCreatedEvent {
    private final Long requestId;
    private final LocalDateTime requestCreationDate;
    private final String requestStatus;
    private final Long bookItemId;
    private final Long userId;
    private final String bookTitle;

    public RequestCreatedEvent(BookItemRequestDto bookItemRequest, String bookTitle) {
        this.requestId = bookItemRequest.getId();
        this.requestCreationDate = bookItemRequest.getCreationDate();
        this.requestStatus = bookItemRequest.getStatus();
        this.bookItemId = bookItemRequest.getBookItemId();
        this.userId = bookItemRequest.getUserId();
        this.bookTitle = bookTitle;
    }
}


