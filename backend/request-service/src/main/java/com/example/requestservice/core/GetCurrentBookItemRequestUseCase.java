package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetCurrentBookItemRequestUseCase {
    private final BookItemRequestService bookItemRequestService;

    BookItemRequest execute(BookItemId bookItemId, UserId userId) {
        return bookItemRequestService.getCurrentBookItemRequest(bookItemId, userId);
    }
}
