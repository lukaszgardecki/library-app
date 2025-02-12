package com.example.libraryapp.application.bookitemrequest;

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
            throw new BookItemRequestException("Book item request status is not " + requiredStatus);
        }
        return bookItemRequest.getId();
    }
}
