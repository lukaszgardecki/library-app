package com.example.libraryapp.web;

import com.example.libraryapp.domain.lending.LendingService;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.MemberService;
import com.example.libraryapp.management.ActionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/lendings")
@RequiredArgsConstructor
public class LendingController {
    private final LendingService lendingService;
    private final MemberService memberService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PagedModel<LendingDto>> getAllLendings(
            @RequestParam(required = false) Long memberId, Pageable pageable
    ) {
        memberService.checkIfAdminOrDataOwnerRequested(memberId);
        PagedModel<LendingDto> collectionModel = lendingService.findLendings(memberId, pageable);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LendingDto> getLendingById(@PathVariable Long id) {
        LendingDto lending = lendingService.findLendingById(id);
        memberService.checkIfAdminOrDataOwnerRequested(lending.getMember().getId());
        return ResponseEntity.ok(lending);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> borrowABook(@RequestBody ActionRequest request) {
        LendingDto savedCheckout = lendingService.borrowABook(request);
        URI savedCheckoutUri = createURI(savedCheckout);
        return ResponseEntity.created(savedCheckoutUri).body(savedCheckout);
    }

    @PostMapping("/renew")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> renewABook(@RequestParam String bookBarcode) {
        LendingDto renewedLending = lendingService.renewABook(bookBarcode);
        return ResponseEntity.ok(renewedLending);
    }

    @PatchMapping("/return")
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
