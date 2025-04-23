package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.domain.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.model.WarehouseBookItemRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemRequest {
    private final BookItemRequestService bookItemRequestService;

    WarehouseBookItemRequest execute(BookItemRequestDto request) {
        return bookItemRequestService.getWarehouseBookItemRequest(request);
    }
}
