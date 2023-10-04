package com.example.libraryapp.web;

import com.example.libraryapp.domain.exception.ReservationNotFoundException;
import com.example.libraryapp.domain.reservation.ReservationDto;
import com.example.libraryapp.domain.reservation.ReservationService;
import com.example.libraryapp.domain.reservation.ReservationToSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/reservations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PagedModel<ReservationDto>> getAllReservations(
            @RequestParam(required = false) Long userId, Pageable pageable) {
        PagedModel<ReservationDto> collectionModel;
        if (userId != null) {
            collectionModel = reservationService.findReservationsByUserId(userId, pageable);
        } else {
            collectionModel = reservationService.findAllReservations(pageable);
        }
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/reservations/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        return reservationService.findReservationById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(ReservationNotFoundException::new);
    }

    @PostMapping("/reservations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> makeAReservation(@RequestBody ReservationToSaveDto reservation) {
        ReservationDto savedReservation = reservationService.makeAReservation(reservation);
        URI savedReservationUri = createURI(savedReservation);
        return ResponseEntity.created(savedReservationUri).body(savedReservation);
    }

    @DeleteMapping("/reservations/{id}")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<?> deleteAReservation(@PathVariable Long id) {
        reservationService.removeAReservation(id);
        return ResponseEntity.noContent().build();
    }

    private URI createURI(ReservationDto savedReservation) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReservation.getId())
                .toUri();
    }
}