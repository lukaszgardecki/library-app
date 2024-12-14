package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CheckBookItemRequestStatusUseCase {
    private final BookItemRequestService bookItemRequestService;

    Long execute(Long bookItemId, Long userId, BookItemRequestStatus requiredStatus) {
        BookItemRequest bookItemRequest = bookItemRequestService.findBookItemRequest(bookItemId, userId);
        if (bookItemRequest.getStatus() != requiredStatus) {
            throw new BookItemRequestException("Book item request status is not " + requiredStatus);
        }
        return bookItemRequest.getId();
    }
}
