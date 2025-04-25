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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book-requests")
class BookRequestController {
    private final BookItemRequestFacade bookItemRequestFacade;

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<Page<BookItemRequestDto>> getAllRequests(
            @RequestParam(required = false) BookItemRequestStatus status,
            Pageable pageable
    ) {
        Page<BookItemRequestDto> page = bookItemRequestFacade.getPageOfBookRequestsByStatus(status, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookItemRequestDto> getBookItemRequestById(@PathVariable Long id) {
        BookItemRequestDto request = bookItemRequestFacade.getBookItemRequestById(new RequestId(id));
        return ResponseEntity.ok(request);
    }

    @GetMapping("/{bookItemId}/isRequested")
    public ResponseEntity<Boolean> isBookItemRequested(@PathVariable Long bookItemId) {
        boolean isRequested = bookItemRequestFacade.isBookItemRequested(new BookItemId(bookItemId));
        return ResponseEntity.ok(isRequested);
    }

    @GetMapping("/current")
    public ResponseEntity<List<BookItemRequestDto>> getUserCurrentBookItemRequests(
            @RequestParam("user_id") Long userId
    ) {
        List<BookItemRequestDto> list = bookItemRequestFacade.getUserCurrentBookItemRequests(new UserId(userId));
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/{requestId}/{status}")
    public ResponseEntity<Void> changeBookItemRequestStatus(
            @PathVariable Long requestId,
            @PathVariable BookItemRequestStatus status
    ) {
        bookItemRequestFacade.changeBookItemRequestStatus(new RequestId(requestId), status);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id.value")
    public ResponseEntity<BookItemRequestDto> requestBookItem(
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
//    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<Void> cancelAllBookItemRequests(@PathVariable String userId) {
        bookItemRequestFacade.cancelAllItemRequestsByUserId(new UserId(Long.parseLong(userId)));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/book-item/{bookItemId}/user/{userId}/ready")
    public ResponseEntity<Long> checkIfBookItemRequestStatusIsReady(
            @PathVariable Long bookItemId,
            @PathVariable Long userId
    ) {
        RequestId requestId = bookItemRequestFacade.checkIfBookItemRequestStatusIsReady(
                new BookItemId(bookItemId), new UserId(userId)
        );
        return ResponseEntity.ok(requestId.value());
    }

    @GetMapping("/book-item/{bookItemId}/check-not-requested")
    public ResponseEntity<Void> ensureBookItemNotRequested(@PathVariable Long bookItemId) {
        bookItemRequestFacade.ensureBookItemNotRequested(new BookItemId(bookItemId));
        return ResponseEntity.noContent().build();
    }
}
