package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.mapper.UserDtoMapper;
import com.example.libraryapp.web.CheckoutController;
import com.example.libraryapp.web.ReservationController;
import com.example.libraryapp.web.UserController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserDto> {

    public UserModelAssembler() {
        super(UserController.class, UserDto.class);
    }

    @Override
    public UserDto toModel(User user) {
        UserDto userDto = UserDtoMapper.map(user);
        userDto.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        userDto.add(linkTo(methodOn(UserController.class).getAllUsers(null)).withRel(IanaLinkRelations.COLLECTION));
        userDto.add(linkTo(methodOn(CheckoutController.class).getAllCheckouts(user.getId())).withRel("checkouts"));
        userDto.add(linkTo(methodOn(ReservationController.class).getAllReservations(user.getId())).withRel("reservations"));
        return userDto;
    }

    @Override
    public CollectionModel<UserDto> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserDto> entityModel = super.toCollectionModel(entities);
        entityModel.add(linkTo(methodOn(UserController.class).getAllUsers(null)).withSelfRel());
        return entityModel;
    }
}
