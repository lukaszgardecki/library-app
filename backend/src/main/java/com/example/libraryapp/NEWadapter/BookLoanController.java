package com.example.libraryapp.NEWadapter;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWapplication.bookitemloan.BookItemLoanFacade;
import com.example.libraryapp.NEWdomain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.NEWinfrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.libraryapp.NEWdomain.user.model.Role.ADMIN;
import static com.example.libraryapp.NEWdomain.user.model.Role.USER;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class BookLoanController {
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
        BookItemLoanDto lending = bookItemLoanFacade.getBookLoan(id);
        authFacade.validateOwnerOrAdminAccess(lending.getMember().getId());
        return ResponseEntity.ok(lending);
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
        authFacade.validateOwnerOrAdminAccess(request.getMemberId());
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

    @PostMapping("/{id}/lost")
    @RoleAuthorization({ADMIN})
    public ResponseEntity<LendingDto> setBookLost(@PathVariable("id") Long lendingId) {
        LendingDto returnedBook = bookItemLoanFacade.setLendingLost(lendingId);
        return ResponseEntity.ok(returnedBook);
    }

    private URI createURI(Long sourceId) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sourceId)
                .toUri();
    }
}
