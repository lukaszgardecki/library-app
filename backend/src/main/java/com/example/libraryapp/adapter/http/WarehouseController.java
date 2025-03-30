package com.example.libraryapp.adapter.http;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.application.warehouse.WarehouseFacade;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.warehouse.dto.WarehouseBookItemRequestListViewDto;
import com.example.libraryapp.domain.warehouse.model.WarehouseBookItemRequest;
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
@RequestMapping("/api/v1/warehouse")
class WarehouseController {
    private final WarehouseFacade warehouseFacade;
    private final BookItemRequestFacade bookItemRequestFacade;

    @GetMapping("/book-requests/list")
    public ResponseEntity<Page<WarehouseBookItemRequestListViewDto>> getBookItemRequestListView(
            @RequestParam(required = false) BookItemRequestStatus status, Pageable pageable
    ) {
        Page<WarehouseBookItemRequestListViewDto> page = warehouseFacade.getBookItemRequestList(status, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/book-requests/{id}/ready")
    public ResponseEntity<Void> completeRequest(@PathVariable("id") Long requestId) {
        bookItemRequestFacade.changeBookRequestStatusToReady(new RequestId(requestId));
        return ResponseEntity.ok().build();
    }
}
