package com.example.libraryapp.adapter;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.infrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.libraryapp.domain.user.model.Role.ADMIN;
import static com.example.libraryapp.domain.user.model.Role.WAREHOUSE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book-requests")
class BookRequestController {
    private final BookItemRequestFacade bookItemRequestFacade;

    @PostMapping
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
    @RoleAuthorization({ADMIN, WAREHOUSE})
    public ResponseEntity<Page<BookItemRequestDto>> getAllPendingReservations(Pageable pageable) {
        Page<BookItemRequestDto> page = bookItemRequestFacade.getPageOfBookRequestsByStatus(BookItemRequestStatus.PENDING, pageable);
        return ResponseEntity.ok(page);
    }


    @PostMapping("/{id}/ready")
    @RoleAuthorization({ADMIN, WAREHOUSE})
    public ResponseEntity<Void> completeRequest(@PathVariable("id") Long requestId) {
        bookItemRequestFacade.changeBookRequestStatusToReady(requestId);
        return ResponseEntity.ok().build();
    }
}
