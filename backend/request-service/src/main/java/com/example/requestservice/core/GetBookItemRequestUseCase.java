package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.ports.out.SourceValidatorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemRequestUseCase {
    private final BookItemRequestService bookItemRequestService;
    private final SourceValidatorPort sourceValidator;

    BookItemRequest execute(RequestId requestId) {
        BookItemRequest request = bookItemRequestService.getBookItemRequestById(requestId);
        sourceValidator.validateUserIsOwner(request.getUserId());
        return request;
    }
}
