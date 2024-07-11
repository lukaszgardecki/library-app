package com.example.libraryapp.web.warehouse;

import com.example.libraryapp.domain.config.RoleAuthorization;
import com.example.libraryapp.domain.reservation.ReservationService;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.example.libraryapp.domain.member.Role.ADMIN;
import static com.example.libraryapp.domain.member.Role.WAREHOUSE;

@RestController
@RequestMapping(value = "/api/v1/warehouse", produces = MediaType.APPLICATION_JSON_VALUE)
@RoleAuthorization({WAREHOUSE, ADMIN})
@RequiredArgsConstructor
public class WarehouseController {
    private final ReservationService reservationService;

    @GetMapping("/reservations/pending")
    public ResponseEntity<List<ReservationResponse>> getAllPendingReservations() {
        List<ReservationResponse> allPendingReservations = reservationService.findAllPendingReservations();
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
