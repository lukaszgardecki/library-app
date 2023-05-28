package com.example.libraryapp.web;

import com.example.libraryapp.domain.config.assembler.ReservationModelAssembler;
import com.example.libraryapp.domain.exception.BookIsNotAvailableException;
import com.example.libraryapp.domain.exception.BookNotFoundException;
import com.example.libraryapp.domain.exception.ReservationNotFoundException;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.reservation.ReservationDto;
import com.example.libraryapp.domain.reservation.ReservationService;
import com.example.libraryapp.domain.reservation.ReservationToSaveDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationModelAssembler reservationModelAssembler;

    public ReservationController(ReservationService reservationService,
                                 ReservationModelAssembler reservationModelAssembler) {
        this.reservationService = reservationService;
        this.reservationModelAssembler = reservationModelAssembler;
    }

    @GetMapping("/reservations")
    public ResponseEntity<CollectionModel<EntityModel<ReservationDto>>> getAllReservations(
            @RequestParam(required = false) Long userId) {
        List<ReservationDto> allUsersReservations;
        CollectionModel<EntityModel<ReservationDto>> collectionModel;

        try {
            if (userId != null) {
                allUsersReservations = reservationService.findReservationsByUserId(userId);
                collectionModel = reservationModelAssembler.toCollectionModel(allUsersReservations);
                collectionModel.add(linkTo(methodOn(ReservationController.class).getAllReservations(userId)).withSelfRel());
            } else {
                allUsersReservations = reservationService.findAllReservations();
                collectionModel = reservationModelAssembler.toCollectionModel(allUsersReservations);
                collectionModel.add(linkTo(ReservationController.class).slash("reservations").withSelfRel());
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        if (allUsersReservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<EntityModel<ReservationDto>> getReservationById(@PathVariable Long id) {
        return reservationService.findReservationById(id)
                .map(reservationModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> makeAReservation(@RequestBody ReservationToSaveDto reservation) {
        ReservationDto savedReservation;
        try {
            savedReservation = reservationService.makeAReservation(reservation);
        } catch (BookIsNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Reason", e.getMessage())
                    .build();
        } catch (UserNotFoundException | BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<ReservationDto> entityModel = reservationModelAssembler.toModel(savedReservation);
        URI savedReservationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedReservation.getId())
                .toUri();
        return ResponseEntity.created(savedReservationUri).body(entityModel);
    }

    @DeleteMapping("/reservations/{id}")
    ResponseEntity<?> deleteAReservation(@PathVariable Long id) {
        try {
            reservationService.removeAReservation(id);
            return ResponseEntity.noContent().build();
        } catch (ReservationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}