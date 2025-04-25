package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.RequestId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemRequestUseCase {
    private final BookItemRequestService bookItemRequestService;

    BookItemRequest execute(RequestId requestId) {
        return bookItemRequestService.getBookItemRequestById(requestId);
    }
}
