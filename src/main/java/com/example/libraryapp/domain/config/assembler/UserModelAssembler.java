package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.web.CheckoutController;
import com.example.libraryapp.web.ReservationController;
import com.example.libraryapp.web.UserController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {


    @Override
    public EntityModel<UserDto> toModel(UserDto user) {
        EntityModel<UserDto> checkoutModel = EntityModel.of(user);
        checkoutModel.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        checkoutModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel(IanaLinkRelations.COLLECTION));
        checkoutModel.add(linkTo(methodOn(CheckoutController.class).getAllCheckouts(user.getId())).withRel("checkouts"));
        checkoutModel.add(linkTo(methodOn(ReservationController.class).getAllReservations(user.getId())).withRel("reservations"));
        return checkoutModel;
    }

    @Override
    public CollectionModel<EntityModel<UserDto>> toCollectionModel(Iterable<? extends UserDto> entities) {
        CollectionModel<EntityModel<UserDto>> entityModel = RepresentationModelAssembler.super.toCollectionModel(entities);
        entityModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
        return entityModel;
    }
}
