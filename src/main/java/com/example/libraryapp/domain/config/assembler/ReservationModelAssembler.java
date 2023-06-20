package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.reservation.Reservation;
import com.example.libraryapp.domain.reservation.ReservationDto;
import com.example.libraryapp.domain.reservation.ReservationDtoMapper;
import com.example.libraryapp.web.ReservationController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class ReservationModelAssembler extends RepresentationModelAssemblerSupport<Reservation, ReservationDto> {


    public ReservationModelAssembler() {
        super(ReservationController.class, ReservationDto.class);
    }

    @Override
    public ReservationDto toModel(Reservation entity) {
        ReservationDto reservationDto = ReservationDtoMapper.map(entity);
        reservationDto.add(linkTo(methodOn(ReservationController.class).getReservationById(entity.getId())).withSelfRel());
        reservationDto.add(linkTo(methodOn(ReservationController.class).getAllReservations(entity.getUser().getId(), null)).withRel(IanaLinkRelations.COLLECTION));
        return reservationDto;
    }

    @Override
    public CollectionModel<ReservationDto> toCollectionModel(Iterable<? extends Reservation> entities) {
        CollectionModel<ReservationDto> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(ReservationController.class).getAllReservations(null, null)).withSelfRel());
        return collectionModel;
    }
}
