package com.example.requestservice.infrastructure.http;

import com.example.requestservice.core.BookItemRequestFacade;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.BookItemRequestStatus;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.model.values.UserId;
import com.example.requestservice.infrastructure.http.dto.BookItemRequestDto;
import com.example.requestservice.infrastructure.http.dto.WarehouseBookItemRequestListViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-requests")
@PreAuthorize("isAuthenticated()")
class BookRequestController {
    private final BookItemRequestFacade bookItemRequestFacade;
    private final BookItemRequestMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    ResponseEntity<Page<BookItemRequestDto>> getAllRequests(
            @RequestParam(required = false) BookItemRequestStatus status,
            Pageable pageable
    ) {
        Page<BookItemRequestDto> page = bookItemRequestFacade.getPageOfBookRequestsByStatus(status, pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/warehouse/list")
    ResponseEntity<Page<WarehouseBookItemRequestListViewDto>> getBookItemRequestListView(
            @RequestParam(required = false) BookItemRequestStatus status, Pageable pageable
    ) {
        Page<WarehouseBookItemRequestListViewDto> page = bookItemRequestFacade
                .getPageOfBookRequestsByStatus(status, pageable)
                .map(mapper::toWarehouseDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<BookItemRequestDto> getBookItemRequestById(@PathVariable Long id) {
        BookItemRequestDto request = mapper.toDto(bookItemRequestFacade.getBookItemRequestById(new RequestId(id)));
        return ResponseEntity.ok(request);
    }

    @GetMapping("/{bookItemId}/isRequested")
    ResponseEntity<Boolean> isBookItemRequested(@PathVariable Long bookItemId) {
        boolean isRequested = bookItemRequestFacade.isBookItemRequested(new BookItemId(bookItemId));
        return ResponseEntity.ok(isRequested);
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE') or #userId == principal")
    ResponseEntity<List<BookItemRequestDto>> getUserCurrentBookItemRequests(
            @RequestParam("user_id") Long userId
    ) {
        List<BookItemRequestDto> list = bookItemRequestFacade.getUserCurrentBookItemRequests(new UserId(userId)).stream()
                .map(mapper::toDto).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{requestId}/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    ResponseEntity<Void> changeBookItemRequestStatus(
            @PathVariable Long requestId,
            @PathVariable BookItemRequestStatus status
    ) {
        bookItemRequestFacade.changeBookItemRequestStatus(new RequestId(requestId), status);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("/{requestId}/ready")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    ResponseEntity<Void> changeBookRequestStatusToReady(@PathVariable Long requestId) {
        bookItemRequestFacade.changeBookRequestStatusToReady(new RequestId(requestId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    ResponseEntity<BookItemRequestDto> requestBookItem(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        BookItemRequestDto savedBookRequest = mapper.toDto(bookItemRequestFacade.requestBookItem(new BookItemId(bookItemId), new UserId(userId)));
        URI savedLoanURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBookRequest.getId())
                .toUri();
        return ResponseEntity.created(savedLoanURI).body(savedBookRequest);
    }

    @DeleteMapping("/users/{userId}/cancel-all")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    ResponseEntity<Void> cancelAllBookItemRequests(@PathVariable Long userId) {
        bookItemRequestFacade.cancelAllBookItemRequestsByUserId(new UserId(userId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/book-item/{bookItemId}/user/{userId}/ready")
    ResponseEntity<Long> checkIfBookItemRequestStatusIsReady(
            @PathVariable Long bookItemId,
            @PathVariable Long userId
    ) {
        RequestId requestId = bookItemRequestFacade.checkIfBookItemRequestStatusIsReady(
                new BookItemId(bookItemId), new UserId(userId)
        );
        return ResponseEntity.ok(requestId.value());
    }

    @GetMapping("/book-item/{bookItemId}/check-not-requested")
    ResponseEntity<Void> ensureBookItemNotRequested(@PathVariable Long bookItemId) {
        bookItemRequestFacade.ensureBookItemNotRequested(new BookItemId(bookItemId));
        return ResponseEntity.noContent().build();
    }
}
