package com.example.libraryapp.domain.book.assembler;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.mapper.BookMapper;
import com.example.libraryapp.web.BookController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class BookModelAssembler extends RepresentationModelAssemblerSupport<Book, BookDto> {

    public BookModelAssembler() {
        super(BookController.class, BookDto.class);
    }

    @Override
    @NonNull
    public BookDto toModel(@NonNull Book book) {
        BookDto bookDto = BookMapper.map(book);
        bookDto.add(linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel());
        bookDto.add(linkTo(methodOn(BookController.class).getAllBookPreviews(null, Collections.emptyList())).withRel(IanaLinkRelations.COLLECTION));
        return bookDto;
    }

    @Override
    @NonNull
    public CollectionModel<BookDto> toCollectionModel(@NonNull Iterable<? extends Book> entities) {
        CollectionModel<BookDto> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(BookController.class).getAllBookPreviews(null, null)).withSelfRel());
        return collectionModel;
    }
}
