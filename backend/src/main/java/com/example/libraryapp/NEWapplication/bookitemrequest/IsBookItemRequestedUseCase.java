package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class IsBookItemRequestedUseCase {
    private final BookItemRequestService bookItemRequestService;

    boolean execute(Long bookItemId) {
        List<BookItemRequestStatus> statusesToFind = List.of(BookItemRequestStatus.PENDING, BookItemRequestStatus.READY);
        List<BookItemRequest> requests = bookItemRequestService.findByBookItemIdAndStatuses(bookItemId, statusesToFind);
        return !requests.isEmpty();
    }
}
