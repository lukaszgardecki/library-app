package com.example.requestservice.core;

import com.example.requestservice.domain.MessageKey;
import com.example.requestservice.domain.exceptions.BookItemRequestException;
import com.example.requestservice.domain.model.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CheckIfBookItemRequestStatusIsReadyUseCase {
    private final BookItemRequestService bookItemRequestService;

    RequestId execute(BookItemId bookItemId, UserId userId) {
        BookItemRequestStatus requiredStatus = BookItemRequestStatus.READY;
        BookItemRequest bookItemRequest = bookItemRequestService.getCurrentBookItemRequest(bookItemId, userId);
        if (bookItemRequest.getStatus() != requiredStatus) {
            throw new BookItemRequestException(MessageKey.REQUEST_STATUS_NOT_READY);
        }
        return bookItemRequest.getId();
    }
}
