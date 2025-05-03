package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.UserId;
import com.example.requestservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CancelBookItemRequestUseCase {
    private final BookItemRequestService bookItemRequestService;
    private final EventPublisherPort publisher;

    void execute(BookItemId bookItemId, UserId userId) {
        BookItemRequest request = bookItemRequestService.getCurrentBookItemRequest(bookItemId, userId);
        bookItemRequestService.cancelRequest(request.getId());
        publisher.publishRequestCanceledEvent(request.getBookItemId(), request.getUserId());
    }
}
