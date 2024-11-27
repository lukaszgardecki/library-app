package com.example.libraryapp.domain.action.assembler;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.action.ActionDto;
import com.example.libraryapp.domain.action.ActionDtoMapper;
import com.example.libraryapp.web.ActionController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class ActionModelAssembler extends RepresentationModelAssemblerSupport<Action, ActionDto> {


    public ActionModelAssembler() {
        super(ActionController.class, ActionDto.class);
    }

    @Override
    @NonNull
    public ActionDto toModel(@NonNull Action entity) {
        ActionDto actionDto = ActionDtoMapper.map(entity);
        actionDto.add(linkTo(methodOn(ActionController.class).getActionById(entity.getId())).withSelfRel());
        actionDto.add(linkTo(methodOn(ActionController.class).getAllActions(entity.getMemberId(), null, null)).withRel(IanaLinkRelations.COLLECTION));
        return actionDto;
    }

    @Override
    @NonNull
    public CollectionModel<ActionDto> toCollectionModel(@NonNull Iterable<? extends Action> entities) {
        CollectionModel<ActionDto> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(ActionController.class).getAllActions(null, null, null)).withSelfRel());
        return collectionModel;
    }
}
