package com.example.loanservice.infrastructure.integration.catalogservice;

import com.example.loanservice.domain.integration.catalogservice.book.Book;
import com.example.loanservice.domain.integration.catalogservice.book.values.*;
import com.example.loanservice.infrastructure.integration.catalogservice.dto.BookDto;

class BookMapper {

    static BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId().value())
                .title(book.getTitle().value())
                .subject(book.getSubject().value())
                .publisher(book.getPublisher().value())
                .ISBN(book.getISBN().value())
                .language(book.getLanguage().value())
                .pages(book.getPages().value())
                .format(book.getFormat().name())
                .publicationDate(book.getPublicationDate().value())
                .build();
    }

    static Book toModel(BookDto dto) {
        return Book.builder()
                .id(new BookId(dto.getId()))
                .title(new Title(dto.getTitle()))
                .subject(new Subject(dto.getSubject()))
                .publisher(new Publisher(dto.getPublisher()))
                .ISBN(new Isbn(dto.getISBN()))
                .language(new Language(dto.getLanguage()))
                .pages(new Pages(dto.getPages()))
                .format(BookFormat.valueOf(dto.getFormat()))
                .publicationDate(new PublicationDate(dto.getPublicationDate()))
                .build();
    }
}
