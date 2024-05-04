package com.example.libraryapp.web.warehouse;

import com.example.libraryapp.domain.reservation.ReservationService;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/warehouse", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyRole('WAREHOUSE', 'ADMIN')")
@RequiredArgsConstructor
public class WarehouseController {
    private final ReservationService reservationService;

    @GetMapping("/reservations/pending")
    public ResponseEntity<PagedModel<ReservationResponse>> getAllPendingReservations(Pageable pageable) {
        PagedModel<ReservationResponse> allPendingReservations = reservationService.findAllPendingReservations(pageable);
        return ResponseEntity.ok(allPendingReservations);
    }

    @PostMapping("/reservations/{id}/ready")
    public ResponseEntity<ReservationResponse> completeReservation(@PathVariable("id") Long reservationId) {
        ReservationResponse reservationResponse = reservationService.changeReservationStatusToReady(reservationId);
        URI reservationURI = createURI(reservationResponse);
        return ResponseEntity.created(reservationURI).body(reservationResponse);
    }

    private URI createURI(ReservationResponse savedReservation) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReservation.getId())
                .toUri();
    }
}
