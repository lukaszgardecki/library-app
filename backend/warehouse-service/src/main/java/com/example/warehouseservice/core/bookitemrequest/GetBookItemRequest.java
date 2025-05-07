package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.domain.integration.request.RequestId;
import com.example.warehouseservice.domain.model.WarehouseBookItemRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemRequest {
    private final BookItemRequestService bookItemRequestService;

    WarehouseBookItemRequest execute(RequestId requestId) {
        return bookItemRequestService.getWarehouseBookItemRequest(requestId);
    }
}
