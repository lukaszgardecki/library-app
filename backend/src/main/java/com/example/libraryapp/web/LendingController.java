package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.config.RoleAuthorization;
import com.example.libraryapp.domain.lending.LendingService;
import com.example.libraryapp.domain.lending.LendingStatus;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.management.ActionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.libraryapp.domain.member.Role.ADMIN;
import static com.example.libraryapp.domain.member.Role.USER;

@RestController
@RequestMapping("/api/v1/lendings")
@RequiredArgsConstructor
public class LendingController {
    private final LendingService lendingService;
    private final AuthenticationService authService;

    @GetMapping
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<PagedModel<LendingDto>> getAllLendings(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) LendingStatus status,
            @RequestParam(required = false) Boolean renewable,
            Pageable pageable
    ) {
        authService.checkIfAdminOrDataOwnerRequested(memberId);
        PagedModel<LendingDto> collectionModel = lendingService.findLendings(memberId, status, pageable, renewable);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<LendingDto> getLendingById(@PathVariable Long id) {
        LendingDto lending = lendingService.findLendingById(id);
        authService.checkIfAdminOrDataOwnerRequested(lending.getMember().getId());
        return ResponseEntity.ok(lending);
    }

    @PostMapping
    @RoleAuthorization({ADMIN})
    public ResponseEntity<LendingDto> borrowABook(@RequestBody ActionRequest request) {
        LendingDto savedCheckout = lendingService.borrowABook(request);
        URI savedCheckoutUri = createURI(savedCheckout);
        return ResponseEntity.created(savedCheckoutUri).body(savedCheckout);
    }

    @PostMapping("/renew")
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<LendingDto> renewABook(@RequestBody ActionRequest request) {
        authService.checkIfAdminOrDataOwnerRequested(request.getMemberId());
        LendingDto renewedLending = lendingService.renewABook(request.getBookBarcode());
        return ResponseEntity.ok(renewedLending);
    }

    @PatchMapping("/return")
    @RoleAuthorization({ADMIN})
    public ResponseEntity<LendingDto> returnABook(@RequestParam String bookBarcode) {
        LendingDto returnedBook = lendingService.returnABook(bookBarcode);
        return ResponseEntity.ok(returnedBook);
    }

    @PostMapping("/{id}/lost")
    @RoleAuthorization({ADMIN})
    public ResponseEntity<LendingDto> setBookLost(@PathVariable("id") Long lendingId) {
        LendingDto returnedBook = lendingService.setLendingLost(lendingId);
        return ResponseEntity.ok(returnedBook);
    }

    private URI createURI(LendingDto savedCheckout) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCheckout.getId())
                .toUri();
    }
}
