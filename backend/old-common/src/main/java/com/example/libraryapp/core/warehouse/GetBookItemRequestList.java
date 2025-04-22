package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.warehouse.model.WarehouseBookItemRequest;
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
