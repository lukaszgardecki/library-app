package com.example.requestservice.core;

import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.BookItemRequestStatus;
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
