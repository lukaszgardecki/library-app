package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.domain.integration.request.RequestId;
import com.example.warehouseservice.domain.ports.out.BookItemRequestServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusToReadyUseCase {
    private final BookItemRequestServicePort bookItemRequestService;

    void execute(RequestId requestId) {
        bookItemRequestService.changeBookRequestStatusToReady(requestId);
    }
}
