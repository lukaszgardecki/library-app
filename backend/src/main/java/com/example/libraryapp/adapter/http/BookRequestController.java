package com.example.libraryapp.adapter.http;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.user.model.UserId;
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

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE')")
    public ResponseEntity<Page<BookItemRequestDto>> getAllRequests(@RequestParam(required = false) BookItemRequestStatus status, Pageable pageable) {
        Page<BookItemRequestDto> page = bookItemRequestFacade.getPageOfBookRequestsByStatus(status, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
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
}
