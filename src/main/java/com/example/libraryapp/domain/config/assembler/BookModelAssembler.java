package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.web.BookController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class BookModelAssembler implements RepresentationModelAssembler<BookDto, EntityModel<BookDto>> {

    @Override
    public EntityModel<BookDto> toModel(BookDto book) {
        EntityModel<BookDto> bookModel = EntityModel.of(book);
        bookModel.add(linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel());
        bookModel.add(linkTo(methodOn(BookController.class).getAllBooks()).withRel(IanaLinkRelations.COLLECTION));
        return bookModel;
    }

    @Override
    public CollectionModel<EntityModel<BookDto>> toCollectionModel(Iterable<? extends BookDto> entities) {
        CollectionModel<EntityModel<BookDto>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel());
        return collectionModel;
    }
}
