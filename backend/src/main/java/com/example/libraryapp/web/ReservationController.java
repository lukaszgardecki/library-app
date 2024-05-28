package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.config.RoleAuthorization;
import com.example.libraryapp.domain.reservation.ReservationService;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
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
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final AuthenticationService authService;

    @GetMapping
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<PagedModel<ReservationResponse>> getAllReservations(
            @RequestParam(required = false) Long memberId, Pageable pageable
    ) {
        authService.checkIfAdminOrDataOwnerRequested(memberId);
        PagedModel<ReservationResponse> collectionModel = reservationService.findReservations(memberId, pageable);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        ReservationResponse reservation = reservationService.findReservationById(id);
        if (reservation.getMember() != null) {
            authService.checkIfAdminOrDataOwnerRequested(reservation.getMember().getId());
        } else {
            authService.checkIfAdminRequested();
        }
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> makeAReservation(@RequestBody ActionRequest request) {
        authService.checkIfAdminOrDataOwnerRequested(request.getMemberId());
        ReservationResponse savedReservation = reservationService.makeAReservation(request);
        URI savedReservationUri = createURI(savedReservation);
        return ResponseEntity.created(savedReservationUri).body(savedReservation);
    }

    @DeleteMapping
    @RoleAuthorization({USER, ADMIN})
    ResponseEntity<?> cancelAReservation(@RequestBody ActionRequest request) {
        authService.checkIfAdminOrDataOwnerRequested(request.getMemberId());
        reservationService.cancelAReservation(request);
        return ResponseEntity.noContent().build();
    }

    private URI createURI(ReservationResponse savedReservation) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReservation.getId())
                .toUri();
    }
}