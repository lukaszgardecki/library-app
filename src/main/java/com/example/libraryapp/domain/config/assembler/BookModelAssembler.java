package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.mapper.BookDtoMapper;
import com.example.libraryapp.web.BookController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class BookModelAssembler extends RepresentationModelAssemblerSupport<Book, BookDto> {

    public BookModelAssembler() {
        super(BookController.class, BookDto.class);
    }

    @Override
    public BookDto toModel(Book book) {
        BookDto bookDto = BookDtoMapper.map(book);
        bookDto.add(linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel());
        bookDto.add(linkTo(methodOn(BookController.class).getAllBooks(null)).withRel(IanaLinkRelations.COLLECTION));
        return bookDto;
    }

    @Override
    public CollectionModel<BookDto> toCollectionModel(Iterable<? extends Book> entities) {
        CollectionModel<BookDto> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(BookController.class).getAllBooks(null)).withSelfRel());
        return collectionModel;
    }
}
