package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.lending.Lending;
import com.example.libraryapp.domain.lending.LendingDto;
import com.example.libraryapp.domain.lending.LendingDtoMapper;
import com.example.libraryapp.web.LendingController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class LendingModelAssembler extends RepresentationModelAssemblerSupport<Lending, LendingDto> {

    public LendingModelAssembler() {
        super(LendingController.class, LendingDto.class);
    }

    @Override
    public LendingDto toModel(Lending lending) {
        LendingDto checkoutModel = LendingDtoMapper.map(lending);
        checkoutModel.add(linkTo(methodOn(LendingController.class).getCheckoutById(lending.getId())).withSelfRel());
        checkoutModel.add(linkTo(LendingController.class).slash("checkouts").withRel(IanaLinkRelations.COLLECTION));
        return checkoutModel;
    }
}