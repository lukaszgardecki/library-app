package com.example.libraryapp.core.bookitemrequest;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.user.model.UserId;
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
