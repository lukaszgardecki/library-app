package com.example.requestservice.infrastructure.http;

import com.example.requestservice.core.BookItemRequestFacade;
import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.BookItemRequestStatus;
import com.example.requestservice.domain.model.RequestId;
import com.example.requestservice.domain.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    ResponseEntity<Page<BookItemRequestDto>> getAllRequests(
            @RequestParam(required = false) BookItemRequestStatus status,
            Pageable pageable
    ) {
        Page<BookItemRequestDto> page = bookItemRequestFacade.getPageOfBookRequestsByStatus(status, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    ResponseEntity<BookItemRequestDto> getBookItemRequestById(@PathVariable Long id) {
        BookItemRequestDto request = bookItemRequestFacade.getBookItemRequestById(new RequestId(id));
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
        List<BookItemRequestDto> list = bookItemRequestFacade.getUserCurrentBookItemRequests(new UserId(userId));
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/{requestId}/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    ResponseEntity<Void> changeBookItemRequestStatus(
            @PathVariable Long requestId,
            @PathVariable BookItemRequestStatus status
    ) {
        bookItemRequestFacade.changeBookItemRequestStatus(new RequestId(requestId), status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{requestId}")
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
        BookItemRequestDto savedBookRequest = bookItemRequestFacade.requestBookItem(new BookItemId(bookItemId), new UserId(userId));
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
