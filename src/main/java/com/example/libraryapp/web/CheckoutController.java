package com.example.libraryapp.web;

import com.example.libraryapp.domain.checkout.CheckoutDto;
import com.example.libraryapp.domain.checkout.CheckoutService;
import com.example.libraryapp.domain.checkout.CheckoutToSaveDto;
import com.example.libraryapp.domain.exception.*;
import com.example.libraryapp.domain.reservation.ReservationDto;
import com.example.libraryapp.domain.reservation.ReservationService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final ReservationService reservationService;

    public CheckoutController(CheckoutService checkoutService,
                              ReservationService reservationService) {
        this.checkoutService = checkoutService;
        this.reservationService = reservationService;
    }


    @GetMapping("/checkouts")
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
    public ResponseEntity<CheckoutDto> getCheckoutById(@PathVariable Long id) {
        return checkoutService.findCheckoutById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(CheckoutNotFoundException::new);
    }

    @PostMapping("/checkouts")
    public ResponseEntity<?> borrowABook(@RequestBody CheckoutToSaveDto checkout) {
        ReservationDto reservation = checkReservation(checkout);
        CheckoutDto savedCheckout = checkoutService.borrowABook(checkout);
        reservationService.removeAReservation(reservation.getId());
        URI savedCheckoutUri = createURI(savedCheckout);
        return ResponseEntity.created(savedCheckoutUri).body(savedCheckout);
    }

    @PatchMapping("/checkouts/return")
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
                .filter(res -> Objects.equals(res.getUserId(), checkout.getUserId()))
                .filter(res -> Objects.equals(res.getBookId(), checkout.getBookId()))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
    }
}
