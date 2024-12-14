package com.example.libraryapp.OLDweb;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWinfrastructure.security.RoleAuthorization;
import com.example.libraryapp.OLDdomain.reservation.ReservationService;
import com.example.libraryapp.OLDdomain.reservation.ReservationStatus;
import com.example.libraryapp.OLDdomain.reservation.dto.ReservationResponse;
import com.example.libraryapp.OLDmanagement.ActionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.libraryapp.OLDdomain.member.Role.ADMIN;
import static com.example.libraryapp.OLDdomain.member.Role.USER;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
//    private final AuthenticationService authService;
    private final AuthenticationFacade authFacade;

    @GetMapping
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<PagedModel<ReservationResponse>> getAllReservations(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) ReservationStatus status,
            Pageable pageable
    ) {
        authFacade.validateOwnerOrAdminAccess(memberId);
        PagedModel<ReservationResponse> collectionModel = reservationService.findReservations(memberId, status, pageable);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        ReservationResponse reservation = reservationService.findReservationById(id);
        authFacade.validateOwnerOrAdminAccess(reservation.getMember().getId());


//        if (reservation.getMember() != null) {
//            authFacade.validateOwnerOrAdminAccess(reservation.getMember().getId());
//        } else {
//            authFacade.checkIfAdminRequested();
//        }
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> makeAReservation(@RequestBody ActionRequest request) {
        authFacade.validateOwnerOrAdminAccess(request.getMemberId());
        ReservationResponse savedReservation = reservationService.makeAReservation(request);
        URI savedReservationUri = createURI(savedReservation);
        return ResponseEntity.created(savedReservationUri).body(savedReservation);
    }

    @DeleteMapping
    @RoleAuthorization({USER, ADMIN})
    ResponseEntity<?> cancelAReservation(@RequestBody ActionRequest request) {
        authFacade.validateOwnerOrAdminAccess(request.getMemberId());
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