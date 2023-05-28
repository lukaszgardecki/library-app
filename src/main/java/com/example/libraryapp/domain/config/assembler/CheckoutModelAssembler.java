package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.checkout.CheckoutDto;
import com.example.libraryapp.web.CheckoutController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class CheckoutModelAssembler implements RepresentationModelAssembler<CheckoutDto, EntityModel<CheckoutDto>> {

    @Override
    public EntityModel<CheckoutDto> toModel(CheckoutDto checkout) {
        EntityModel<CheckoutDto> checkoutModel = EntityModel.of(checkout);
        checkoutModel.add(linkTo(methodOn(CheckoutController.class).getCheckoutById(checkout.getId())).withSelfRel());
        checkoutModel.add(linkTo(CheckoutController.class).slash("checkouts").withRel(IanaLinkRelations.COLLECTION));
        return checkoutModel;
    }
}