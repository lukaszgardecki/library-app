package com.example.libraryapp.domain.rack;

import com.example.libraryapp.web.RackController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class RackModelAssembler extends RepresentationModelAssemblerSupport<Rack, RackDto> {

    public RackModelAssembler() {
        super(RackController.class, RackDto.class);
    }

    @Override
    @NonNull
    public RackDto toModel(@NonNull Rack rack) {
        RackDto rackDto = RackMapper.map(rack);
        rackDto.add(linkTo(methodOn(RackController.class).getRackById(rack.getId())).withSelfRel());
        rackDto.add(linkTo(methodOn(RackController.class).getAllRacks(null)).withRel(IanaLinkRelations.COLLECTION));
        return rackDto;
    }

    @Override
    @NonNull
    public CollectionModel<RackDto> toCollectionModel(@NonNull Iterable<? extends Rack> entities) {
        CollectionModel<RackDto> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(RackController.class).getAllRacks(null)).withSelfRel());
        return collectionModel;
    }
}
