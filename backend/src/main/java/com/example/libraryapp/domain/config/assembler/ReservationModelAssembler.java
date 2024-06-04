package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.reservation.Reservation;
import com.example.libraryapp.domain.reservation.ReservationDtoMapper;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.web.ReservationController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class ReservationModelAssembler extends RepresentationModelAssemblerSupport<Reservation, ReservationResponse> {


    public ReservationModelAssembler() {
        super(ReservationController.class, ReservationResponse.class);
    }

    @Override
    @NonNull
    public ReservationResponse toModel(@NonNull Reservation entity) {
        ReservationResponse reservationResponse = ReservationDtoMapper.map(entity);
        reservationResponse.add(linkTo(methodOn(ReservationController.class).getReservationById(entity.getId())).withSelfRel());
        if (entity.getMember() != null) {
            reservationResponse.add(linkTo(methodOn(ReservationController.class).getAllReservations(entity.getMember().getId(), null, null)).withRel(IanaLinkRelations.COLLECTION));
        }
        return reservationResponse;
    }

    @Override
    @NonNull
    public CollectionModel<ReservationResponse> toCollectionModel(@NonNull Iterable<? extends Reservation> entities) {
        CollectionModel<ReservationResponse> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(ReservationController.class).getAllReservations(null, null, null)).withSelfRel());
        return collectionModel;
    }
}
