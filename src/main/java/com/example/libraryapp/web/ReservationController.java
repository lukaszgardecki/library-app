package com.example.libraryapp.web;

import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import com.example.libraryapp.domain.member.MemberService;
import com.example.libraryapp.domain.notification.NotificationService;
import com.example.libraryapp.domain.reservation.ReservationService;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
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
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final MemberService memberService;
    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PagedModel<ReservationResponse>> getAllReservations(
            @RequestParam(required = false) Long memberId, Pageable pageable) {
        PagedModel<ReservationResponse> collectionModel = null;
        if (memberId != null) {
            memberService.checkIfAdminOrDataOwnerRequested(memberId);
            collectionModel = reservationService.findReservationsByUserId(memberId, pageable);
        } else if (memberService.checkIfCurrentLoggedInUserIsAdmin()) {
            collectionModel = reservationService.findAllReservations(pageable);
        }
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        Optional<ReservationResponse> reservation = reservationService.findReservationById(id);
        reservation.ifPresent(res ->
                memberService.checkIfAdminOrDataOwnerRequested(res.getMember().getId())
        );
        return reservation
                .map(ResponseEntity::ok)
                .orElseThrow(ReservationNotFoundException::new);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> makeAReservation(Long memberId, String bookBarcode) {
        memberService.checkIfAdminOrDataOwnerRequested(memberId);
        ReservationResponse savedReservation = reservationService.makeAReservation(memberId, bookBarcode);
        URI savedReservationUri = createURI(savedReservation);
        notificationService.send(NotificationService.RESERVATION_CREATED);
        return ResponseEntity.created(savedReservationUri).body(savedReservation);
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<?> cancelAReservation(Long memberId, String bookBarcode) {
        memberService.checkIfAdminOrDataOwnerRequested(memberId);
        reservationService.cancelAReservation(memberId, bookBarcode);
        return ResponseEntity.noContent().build();
    }

    private URI createURI(ReservationResponse savedReservation) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReservation.getId())
                .toUri();
    }
}