package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class IsBookItemRequestedUseCase {
    private final BookItemRequestService bookItemRequestService;

    boolean execute(Long bookItemId) {
        return bookItemRequestService.getAllByBookItemIdAndStatuses(bookItemId, List.of(
                BookItemRequestStatus.PENDING,
                BookItemRequestStatus.READY,
                BookItemRequestStatus.RESERVED
        )).stream().findAny().isPresent();
    }
}
