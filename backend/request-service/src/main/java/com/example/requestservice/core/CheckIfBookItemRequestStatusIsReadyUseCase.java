package com.example.requestservice.core;

import com.example.requestservice.domain.exceptions.BookItemRequestException;
import com.example.requestservice.domain.i18n.MessageKey;
import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.BookItemRequestStatus;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.model.values.UserId;
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
