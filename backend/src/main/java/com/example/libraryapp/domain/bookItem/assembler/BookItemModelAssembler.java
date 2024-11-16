package com.example.libraryapp.domain.bookItem.assembler;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.bookItem.mapper.BookItemMapper;
import com.example.libraryapp.web.BookItemController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class BookItemModelAssembler extends RepresentationModelAssemblerSupport<BookItem, BookItemDto> {

    public BookItemModelAssembler() {
        super(BookItemController.class, BookItemDto.class);
    }

    @Override
    @NonNull
    public BookItemDto toModel(@NonNull BookItem bookItem) {
        BookItemDto bookDto = BookItemMapper.map(bookItem);
        bookDto.add(linkTo(methodOn(BookItemController.class).getBookItemById(bookItem.getId())).withSelfRel());
        bookDto.add(linkTo(methodOn(BookItemController.class).getAllBookItems(null)).withRel(IanaLinkRelations.COLLECTION));
        return bookDto;
    }

    @Override
    @NonNull
    public CollectionModel<BookItemDto> toCollectionModel(@NonNull Iterable<? extends BookItem> entities) {
        CollectionModel<BookItemDto> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(BookItemController.class).getAllBookItems(null)).withSelfRel());
        return collectionModel;
    }
}
