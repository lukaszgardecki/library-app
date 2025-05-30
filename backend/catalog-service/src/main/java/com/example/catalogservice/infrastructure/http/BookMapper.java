package com.example.catalogservice.infrastructure.http;

import com.example.catalogservice.domain.model.book.Book;
import com.example.catalogservice.domain.model.book.values.*;
import com.example.catalogservice.infrastructure.http.dto.BookDto;
import com.example.catalogservice.infrastructure.http.dto.BookToSaveDto;

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
                .format(book.getFormat())
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
                .format(dto.getFormat())
                .publicationDate(new PublicationDate(dto.getPublicationDate()))
                .build();
    }

    static Book toModel(BookToSaveDto dto) {
        return Book.builder()
                .title(new Title(dto.getTitle()))
                .subject(new Subject(dto.getSubject()))
                .publisher(new Publisher(dto.getPublisher()))
                .ISBN(new Isbn(dto.getISBN()))
                .language(new Language(dto.getLanguage()))
                .pages(new Pages(dto.getPages()))
                .format(dto.getFormat())
                .publicationDate(new PublicationDate(dto.getPublicationDate()))
                .build();
    }
}
