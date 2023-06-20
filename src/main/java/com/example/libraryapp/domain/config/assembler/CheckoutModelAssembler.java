package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.checkout.Checkout;
import com.example.libraryapp.domain.checkout.CheckoutDto;
import com.example.libraryapp.domain.checkout.CheckoutDtoMapper;
import com.example.libraryapp.web.CheckoutController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class CheckoutModelAssembler extends RepresentationModelAssemblerSupport<Checkout, CheckoutDto> {

    public CheckoutModelAssembler() {
        super(CheckoutController.class, CheckoutDto.class);
    }

    @Override
    public CheckoutDto toModel(Checkout checkout) {
        CheckoutDto checkoutModel = CheckoutDtoMapper.map(checkout);
        checkoutModel.add(linkTo(methodOn(CheckoutController.class).getCheckoutById(checkout.getId())).withSelfRel());
        checkoutModel.add(linkTo(CheckoutController.class).slash("checkouts").withRel(IanaLinkRelations.COLLECTION));
        return checkoutModel;
    }
}