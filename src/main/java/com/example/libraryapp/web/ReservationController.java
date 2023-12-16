package com.example.libraryapp.web;

import com.example.libraryapp.domain.member.MemberService;
import com.example.libraryapp.domain.notification.NotificationService;
import com.example.libraryapp.domain.reservation.ReservationService;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
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
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final MemberService memberService;
    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PagedModel<ReservationResponse>> getAllReservations(
            @RequestParam(required = false) Long memberId, Pageable pageable
    ) {
        memberService.checkIfAdminOrDataOwnerRequested(memberId);
        PagedModel<ReservationResponse> collectionModel = reservationService.findReservations(memberId, pageable);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        ReservationResponse reservation = reservationService.findReservationById(id);
        if (reservation.getMember() != null) {
            memberService.checkIfAdminOrDataOwnerRequested(reservation.getMember().getId());
        } else {
            memberService.checkIfAdminRequested();
        }
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> makeAReservation(@RequestBody ActionRequest request) {
        memberService.checkIfAdminOrDataOwnerRequested(request.getMemberId());
        ReservationResponse savedReservation = reservationService.makeAReservation(request);
        URI savedReservationUri = createURI(savedReservation);
        notificationService.send(NotificationService.RESERVATION_CREATED);
        return ResponseEntity.created(savedReservationUri).body(savedReservation);
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<?> cancelAReservation(@RequestBody ActionRequest request) {
        memberService.checkIfAdminOrDataOwnerRequested(request.getMemberId());
        reservationService.cancelAReservation(request);
        notificationService.send(NotificationService.RESERVATION_DELETED);
        return ResponseEntity.noContent().build();
    }

    private URI createURI(ReservationResponse savedReservation) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReservation.getId())
                .toUri();
    }
}