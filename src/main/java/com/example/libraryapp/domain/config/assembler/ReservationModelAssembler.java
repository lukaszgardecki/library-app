package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.reservation.ReservationDto;
import com.example.libraryapp.web.ReservationController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class ReservationModelAssembler implements RepresentationModelAssembler<ReservationDto, EntityModel<ReservationDto>> {

    @Override
    public EntityModel<ReservationDto> toModel(ReservationDto reservation) {
        EntityModel<ReservationDto> reservationModel = EntityModel.of(reservation);
        reservationModel.add(linkTo(methodOn(ReservationController.class).getReservationById(reservation.getId())).withSelfRel());
        reservationModel.add(linkTo(ReservationController.class).slash("reservations").withRel(IanaLinkRelations.COLLECTION));
        return reservationModel;
    }
}
