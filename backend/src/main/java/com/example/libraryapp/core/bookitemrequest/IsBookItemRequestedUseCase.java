package com.example.libraryapp.core.bookitemrequest;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class IsBookItemRequestedUseCase {
    private final BookItemRequestService bookItemRequestService;

    boolean execute(BookItemId bookItemId) {
        return bookItemRequestService.getAllByBookItemIdAndStatuses(bookItemId, List.of(
                BookItemRequestStatus.PENDING,
                BookItemRequestStatus.READY,
                BookItemRequestStatus.RESERVED
        )).stream().findAny().isPresent();
    }
}
