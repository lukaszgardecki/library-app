package com.example.libraryapp.web;

import com.example.libraryapp.domain.exception.lending.LendingNotFoundException;
import com.example.libraryapp.domain.lending.LendingService;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.MemberService;
import com.example.libraryapp.domain.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LendingController {
    private final LendingService lendingService;
    private final MemberService memberService;
    private final NotificationService notificationService;

    @GetMapping("/lendings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PagedModel<LendingDto>> getAllCheckouts(
            @RequestParam(required = false) Long memberId, Pageable pageable) {
        PagedModel<LendingDto> collectionModel = null;
        if (memberId != null) {
            memberService.checkIfAdminOrDataOwnerRequested(memberId);
            collectionModel = lendingService.findLendingsByMemberId(memberId, pageable);
        } else if (memberService.checkIfCurrentLoggedInUserIsAdmin()) {
            collectionModel = lendingService.findAllCheckouts(pageable);
        }
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/lendings/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LendingDto> getCheckoutById(@PathVariable Long id) {
        Optional<LendingDto> lending = lendingService.findLendingById(id);
        lending.ifPresent(len -> memberService.checkIfAdminOrDataOwnerRequested(len.getMemberId()));
        return lending
                .map(ResponseEntity::ok)
                .orElseThrow(LendingNotFoundException::new);
    }

    @PostMapping("/lendings")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> borrowABook(Long memberId, String bookBarcode) {
        // TODO: 29.11.2023 czy sprawdzanie admin or data owner jest konieczne? tutaj może wejść ponoć tylko admin?
        memberService.checkIfAdminOrDataOwnerRequested(memberId);
        LendingDto savedCheckout = lendingService.borrowABook(memberId, bookBarcode);
        URI savedCheckoutUri = createURI(savedCheckout);
        notificationService.send(NotificationService.BOOK_BORROWED);
        return ResponseEntity.created(savedCheckoutUri).body(savedCheckout);
    }

    @PostMapping("/lendings/renew")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> renewABook(String bookBarcode) {
        LendingDto renewedLending = lendingService.renewABook(bookBarcode);
        URI savedLendingUri = createURI(renewedLending);
        notificationService.send(NotificationService.BOOK_EXTENDED);
        return ResponseEntity.created(savedLendingUri).body(renewedLending);
    }

    @PatchMapping("/lendings/return")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> returnABook(@RequestParam String bookBarcode) {
        LendingDto returnedBook = lendingService.returnABook(bookBarcode);
        return ResponseEntity.ok(returnedBook);
    }

    private URI createURI(LendingDto savedCheckout) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCheckout.getId())
                .toUri();
    }
}
