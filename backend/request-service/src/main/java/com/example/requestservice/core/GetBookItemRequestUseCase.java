package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.RequestId;
import com.example.requestservice.domain.ports.SourceValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemRequestUseCase {
    private final BookItemRequestService bookItemRequestService;
    private final SourceValidator sourceValidator;

    BookItemRequest execute(RequestId requestId) {
        BookItemRequest request = bookItemRequestService.getBookItemRequestById(requestId);
        sourceValidator.validateUserIsOwner(request.getUserId());
        return request;
    }
}
