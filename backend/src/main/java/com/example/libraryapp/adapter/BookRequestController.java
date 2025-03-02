package com.example.libraryapp.adapter;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book-requests")
class BookRequestController {
    private final BookItemRequestFacade bookItemRequestFacade;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.principal.id)")
    public ResponseEntity<BookItemRequestDto> requestBookItem(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        BookItemRequestDto savedBookRequest = bookItemRequestFacade.requestBookItem(bookItemId, userId);
        URI savedLoanURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBookRequest.id())
                .toUri();
        return ResponseEntity.created(savedLoanURI).body(savedBookRequest);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<Page<BookItemRequestDto>> getAllPendingReservations(Pageable pageable) {
        Page<BookItemRequestDto> page = bookItemRequestFacade.getPageOfBookRequestsByStatus(BookItemRequestStatus.PENDING, pageable);
        return ResponseEntity.ok(page);
    }


    @PostMapping("/{id}/ready")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<Void> completeRequest(@PathVariable("id") Long requestId) {
        bookItemRequestFacade.changeBookRequestStatusToReady(requestId);
        return ResponseEntity.ok().build();
    }
}
