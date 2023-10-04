package com.example.libraryapp.web;

import com.example.libraryapp.domain.checkout.CheckoutDto;
import com.example.libraryapp.domain.checkout.CheckoutService;
import com.example.libraryapp.domain.checkout.CheckoutToSaveDto;
import com.example.libraryapp.domain.exception.CheckoutNotFoundException;
import com.example.libraryapp.domain.exception.ReservationNotFoundException;
import com.example.libraryapp.domain.reservation.ReservationDto;
import com.example.libraryapp.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final ReservationService reservationService;

    @GetMapping("/checkouts")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PagedModel<CheckoutDto>> getAllCheckouts(
            @RequestParam(required = false) Long userId, Pageable pageable) {
        PagedModel<CheckoutDto> collectionModel;
        if (userId != null) {
            collectionModel = checkoutService.findCheckoutsByUserId(userId, pageable);
        } else {
            collectionModel = checkoutService.findAllCheckouts(pageable);
        }
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/checkouts/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CheckoutDto> getCheckoutById(@PathVariable Long id) {
        return checkoutService.findCheckoutById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(CheckoutNotFoundException::new);
    }

    @PostMapping("/checkouts")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> borrowABook(@RequestBody CheckoutToSaveDto checkout) {
        ReservationDto reservation = checkReservation(checkout);
        CheckoutDto savedCheckout = checkoutService.borrowABook(checkout);
        reservationService.removeAReservation(reservation.getId());
        URI savedCheckoutUri = createURI(savedCheckout);
        return ResponseEntity.created(savedCheckoutUri).body(savedCheckout);
    }

    @PatchMapping("/checkouts/return")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> returnABook(@RequestParam Long bookId) {
        CheckoutDto returnedBook = checkoutService.returnABook(bookId);
        return ResponseEntity.ok(returnedBook);
    }

    private URI createURI(CheckoutDto savedCheckout) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCheckout.getId())
                .toUri();
    }

    private ReservationDto checkReservation(CheckoutToSaveDto checkout) {
        return reservationService.findAllReservations()
                .stream()
                .filter(res -> Objects.equals(res.getUser().getId(), checkout.getUserId()))
                .filter(res -> Objects.equals(res.getBook().getId(), checkout.getBookId()))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
    }
}
