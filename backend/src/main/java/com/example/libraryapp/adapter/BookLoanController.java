package com.example.libraryapp.adapter;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.bookitemloan.BookItemLoanFacade;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanListPreviewDto;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
class BookLoanController {
    private final BookItemLoanFacade bookItemLoanFacade;
    private final AuthenticationFacade authFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.principal.id)")
    public ResponseEntity<Page<BookItemLoanDto>> getAllBookLoans(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) BookItemLoanStatus status,
            @RequestParam(required = false) Boolean renewable,
            Pageable pageable
    ) {
        Page<BookItemLoanDto> page = bookItemLoanFacade.getPageOfBookLoansByParams(
                userId, status, renewable, pageable
        );
        return ResponseEntity.ok(page);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.principal.id)")
    public ResponseEntity<Page<BookItemLoanListPreviewDto>> getLoanListPreviews(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) BookItemLoanStatus status,
            @RequestParam(required = false) Boolean renewable,
            @RequestParam(value = "q", required = false) String query,
            Pageable pageable
    ) {
        Page<BookItemLoanListPreviewDto> page = bookItemLoanFacade.getPageOfBookLoanListPreviewsByParams(
                userId, query, status, renewable, pageable
        );
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<BookItemLoanDto> getBookLoanById(@PathVariable Long id) {
        BookItemLoanDto loan = bookItemLoanFacade.getBookLoan(id);
        authFacade.validateOwnerOrAdminAccess(loan.userId());
        return ResponseEntity.ok(loan);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookItemLoanDto> borrowABook(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        BookItemLoanDto savedBookItemLoan = bookItemLoanFacade.borrowBookItem(bookItemId, userId);
        URI savedCheckoutUri = createURI(savedBookItemLoan.id());
        return ResponseEntity.created(savedCheckoutUri).body(savedBookItemLoan);
    }

    @PostMapping("/renew")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.principal.id)")
    public ResponseEntity<BookItemLoanDto> renewABook(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        BookItemLoanDto renewedLoan = bookItemLoanFacade.renewBookItemLoan(bookItemId, userId);
        return ResponseEntity.ok(renewedLoan);
    }

    @PatchMapping("/return")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> returnABook(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        bookItemLoanFacade.returnBookItem(bookItemId, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/lost")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> processLostBookItem(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        bookItemLoanFacade.processLostBookItem(bookItemId, userId);
        return ResponseEntity.ok().build();
    }

    private URI createURI(Long sourceId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sourceId)
                .toUri();
    }
}
