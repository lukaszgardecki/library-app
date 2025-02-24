package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CheckIfBookItemRequestStatusIsReadyUseCase {
    private final BookItemRequestService bookItemRequestService;

    Long execute(Long bookItemId, Long userId) {
        BookItemRequestStatus requiredStatus = BookItemRequestStatus.READY;
        BookItemRequest bookItemRequest = bookItemRequestService.getCurrentBookItemRequest(bookItemId, userId);
        if (bookItemRequest.getStatus() != requiredStatus) {
            throw new BookItemRequestException(MessageKey.REQUEST_STATUS_NOT_READY);
        }
        return bookItemRequest.getId();
    }
}
