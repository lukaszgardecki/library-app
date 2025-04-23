package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.domain.model.BookItemRequestStatus;
import com.example.warehouseservice.domain.model.WarehouseBookItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetBookItemRequestList {
    private final BookItemRequestService bookItemRequestService;

    Page<WarehouseBookItemRequest> execute(BookItemRequestStatus status, Pageable pageable) {
        return bookItemRequestService.getBookRequestList(status, pageable);
    }
}
