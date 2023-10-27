package com.example.libraryapp.web;

import com.example.libraryapp.domain.lending.LendingDto;
import com.example.libraryapp.domain.lending.LendingService;
import com.example.libraryapp.domain.lending.LendingToSaveDto;
import com.example.libraryapp.domain.exception.LendingNotFoundException;
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
public class LendingController {
    private final LendingService lendingService;
    private final ReservationService reservationService;

    @GetMapping("/lendings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PagedModel<LendingDto>> getAllCheckouts(
            @RequestParam(required = false) Long userId, Pageable pageable) {
        PagedModel<LendingDto> collectionModel;
        if (userId != null) {
            collectionModel = lendingService.findCheckoutsByUserId(userId, pageable);
        } else {
            collectionModel = lendingService.findAllCheckouts(pageable);
        }
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/lendings/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LendingDto> getCheckoutById(@PathVariable Long id) {
        return lendingService.findCheckoutById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(LendingNotFoundException::new);
    }

    @PostMapping("/lendings")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> borrowABook(@RequestBody LendingToSaveDto checkout) {
        ReservationDto reservation = checkReservation(checkout);
        LendingDto savedCheckout = lendingService.borrowABook(checkout);
        reservationService.removeAReservation(reservation.getId());
        URI savedCheckoutUri = createURI(savedCheckout);
        return ResponseEntity.created(savedCheckoutUri).body(savedCheckout);
    }

    @PatchMapping("/lendings/return")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> returnABook(@RequestParam Long bookId) {
        LendingDto returnedBook = lendingService.returnABook(bookId);
        return ResponseEntity.ok(returnedBook);
    }

    private URI createURI(LendingDto savedCheckout) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCheckout.getId())
                .toUri();
    }

    private ReservationDto checkReservation(LendingToSaveDto checkout) {
        return reservationService.findAllReservations()
                .stream()
                .filter(res -> Objects.equals(res.getUser().getId(), checkout.getUserId()))
                .filter(res -> Objects.equals(res.getBook().getId(), checkout.getBookId()))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
    }
}
