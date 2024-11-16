package com.example.libraryapp.domain.book.assembler;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.dto.BookPreviewDto;
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
public class BookPreviewAssembler extends RepresentationModelAssemblerSupport<Book, BookPreviewDto> {

    public BookPreviewAssembler() {
        super(BookController.class, BookPreviewDto.class);
    }

    @Override
    @NonNull
    public BookPreviewDto toModel(@NonNull Book book) {
        BookPreviewDto bookDto = BookMapper.mapToPreviewDto(book);
        bookDto.add(linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel());
        bookDto.add(linkTo(methodOn(BookController.class).getAllBookPreviews(null, Collections.emptyList())).withRel(IanaLinkRelations.COLLECTION));
        return bookDto;
    }

    @Override
    @NonNull
    public CollectionModel<BookPreviewDto> toCollectionModel(@NonNull Iterable<? extends Book> entities) {
        CollectionModel<BookPreviewDto> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(BookController.class).getAllBookPreviews(null, null)).withSelfRel());
        return collectionModel;
    }
}
