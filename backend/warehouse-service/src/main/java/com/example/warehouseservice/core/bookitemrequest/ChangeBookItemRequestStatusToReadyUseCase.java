package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.ports.BookItemRequestServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusToReadyUseCase {
    private final BookItemRequestServicePort bookItemRequestService;

    void execute(RequestId requestId) {
        bookItemRequestService.changeBookRequestStatusToReady(requestId);
    }
}
