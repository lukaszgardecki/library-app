package com.example.libraryapp.adapter;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.bookitemloan.BookItemLoanFacade;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.infrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.libraryapp.domain.user.model.Role.ADMIN;
import static com.example.libraryapp.domain.user.model.Role.USER;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
class BookLoanController {
    private final BookItemLoanFacade bookItemLoanFacade;
    private final AuthenticationFacade authFacade;

    @GetMapping
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<Page<BookItemLoanDto>> getAllBookLoans(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) BookItemLoanStatus status,
            @RequestParam(required = false) Boolean renewable,
            Pageable pageable
    ) {
        authFacade.validateOwnerOrAdminAccess(userId);
        Page<BookItemLoanDto> collectionModel = bookItemLoanFacade.getPageOfBookLoansByParams(
                userId, status, renewable, pageable
        );
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<BookItemLoanDto> getBookLoanById(@PathVariable Long id) {
        BookItemLoanDto loan = bookItemLoanFacade.getBookLoan(id);
        authFacade.validateOwnerOrAdminAccess(loan.userId());
        return ResponseEntity.ok(loan);
    }

    @PostMapping
    @RoleAuthorization({ADMIN})
    public ResponseEntity<BookItemLoanDto> borrowABook(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        BookItemLoanDto savedBookItemLoan = bookItemLoanFacade.borrowBookItem(bookItemId, userId);
        URI savedCheckoutUri = createURI(savedBookItemLoan.id());
        return ResponseEntity.created(savedCheckoutUri).body(savedBookItemLoan);
    }

    @PostMapping("/renew")
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<BookItemLoanDto> renewABook(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        BookItemLoanDto renewedLoan = bookItemLoanFacade.renewBookItemLoan(bookItemId, userId);
        return ResponseEntity.ok(renewedLoan);
    }

    @PatchMapping("/return")
    @RoleAuthorization({ADMIN})
    public ResponseEntity<Void> returnABook(
            @RequestParam("bi_id") Long bookItemId,
            @RequestParam("user_id") Long userId
    ) {
        bookItemLoanFacade.returnBookItem(bookItemId, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/lost")
    @RoleAuthorization({ADMIN})
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
