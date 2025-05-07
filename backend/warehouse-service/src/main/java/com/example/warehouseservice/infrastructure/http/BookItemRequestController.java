package com.example.warehouseservice.infrastructure.http;

import com.example.warehouseservice.core.bookitemrequest.BookItemRequestFacade;
import com.example.warehouseservice.domain.dto.WarehouseBookItemRequestListViewDto;
import com.example.warehouseservice.domain.integration.request.BookItemRequestStatus;
import com.example.warehouseservice.domain.integration.request.RequestId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
@RequestMapping("/warehouse/book-requests")
class BookItemRequestController {
    private final BookItemRequestFacade bookItemRequestFacade;

    @GetMapping("/list")
    public ResponseEntity<Page<WarehouseBookItemRequestListViewDto>> getBookItemRequestListView(
            @RequestParam(required = false) BookItemRequestStatus status, Pageable pageable
    ) {
        Page<WarehouseBookItemRequestListViewDto> page = bookItemRequestFacade.getBookItemRequestList(status, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/{id}/ready")
    public ResponseEntity<Void> completeRequest(@PathVariable("id") Long requestId) {
        bookItemRequestFacade.changeBookRequestStatusToReady(new RequestId(requestId));
        return ResponseEntity.ok().build();
    }
}
