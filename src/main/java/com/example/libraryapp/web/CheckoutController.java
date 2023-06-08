package com.example.libraryapp.web;

import com.example.libraryapp.domain.checkout.CheckoutDto;
import com.example.libraryapp.domain.checkout.CheckoutService;
import com.example.libraryapp.domain.checkout.CheckoutToSaveDto;
import com.example.libraryapp.domain.config.assembler.CheckoutModelAssembler;
import com.example.libraryapp.domain.exception.BookIsAlreadyReturnedException;
import com.example.libraryapp.domain.exception.BookNotFoundException;
import com.example.libraryapp.domain.exception.ReservationNotFoundException;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.reservation.ReservationDto;
import com.example.libraryapp.domain.reservation.ReservationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final ReservationService reservationService;
    private final CheckoutModelAssembler checkoutModelAssembler;

    public CheckoutController(CheckoutService checkoutService,
                              ReservationService reservationService,
                              CheckoutModelAssembler checkoutModelAssembler) {
        this.checkoutService = checkoutService;
        this.reservationService = reservationService;
        this.checkoutModelAssembler = checkoutModelAssembler;
    }


    @GetMapping("/checkouts")
    public ResponseEntity<CollectionModel<EntityModel<CheckoutDto>>> getAllCheckouts(
            @RequestParam(required = false) Long userId) {
        if (userId != null) {
            return checkoutService.findCheckoutsByUserId(userId)
                    .map(checkoutModelAssembler::toCollectionModel)
                    .map(model -> model.add(linkTo(methodOn(CheckoutController.class).getAllCheckouts(userId)).withSelfRel()))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return checkoutService.findAllCheckouts()
                    .map(checkoutModelAssembler::toCollectionModel)
                    .map(model -> model.add(linkTo(CheckoutController.class).slash("checkouts").withSelfRel()))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }

    @GetMapping("/checkouts/{id}")
    public ResponseEntity<EntityModel<CheckoutDto>> getCheckoutById(@PathVariable Long id) {
        return checkoutService.findCheckoutById(id)
                .map(checkoutModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/checkouts")
    public ResponseEntity<?> borrowABook(@RequestBody CheckoutToSaveDto checkout) {
        CheckoutDto savedCheckout;
        try {
            ReservationDto reservation = checkReservation(checkout);
            savedCheckout = checkoutService.borrowABook(checkout);
            reservationService.removeAReservation(reservation.getId());
        } catch (UserNotFoundException | BookNotFoundException | ReservationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<CheckoutDto> entityModel = checkoutModelAssembler.toModel(savedCheckout);
        URI savedCheckoutUri = createURI(savedCheckout);
        return ResponseEntity.created(savedCheckoutUri).body(entityModel);
    }

    @PatchMapping("/checkouts/return")
    ResponseEntity<?> returnABook(@RequestParam Long bookId) {
        try {
            CheckoutDto returnedBook = checkoutService.returnABook(bookId);
            EntityModel<CheckoutDto> entityModel = checkoutModelAssembler.toModel(returnedBook);
            return ResponseEntity.ok(entityModel);
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BookIsAlreadyReturnedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Reason", e.getMessage())
                    .build();
        }
    }

    private URI createURI(CheckoutDto savedCheckout) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCheckout.getId())
                .toUri();
    }

    private ReservationDto checkReservation(CheckoutToSaveDto checkout) {
        return reservationService.findReservationsByUserId(checkout.getUserId()).stream()
                .filter(res -> Objects.equals(res.getBookId(), checkout.getBookId()))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
    }
}
