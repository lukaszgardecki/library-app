package com.example.libraryapp.NEWapplication.book;

import com.example.libraryapp.NEWdomain.book.dto.BookDto;
import com.example.libraryapp.NEWdomain.book.dto.BookToSaveDto;
import com.example.libraryapp.NEWdomain.book.model.Book;

class BookMapper {

    static BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .subject(book.getSubject())
                .publisher(book.getPublisher())
                .ISBN(book.getISBN())
                .language(book.getLanguage())
                .pages(book.getPages())
                .build();
    }

    static Book toModel(BookDto dto) {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .subject(dto.getSubject())
                .publisher(dto.getPublisher())
                .ISBN(dto.getISBN())
                .language(dto.getLanguage())
                .pages(dto.getPages())
                .build();
    }

    static Book toModel(BookToSaveDto dto) {
        return Book.builder()
                .title(dto.getTitle())
                .subject(dto.getSubject())
                .publisher(dto.getPublisher())
                .ISBN(dto.getISBN())
                .language(dto.getLanguage())
                .pages(dto.getPages())
                .build();
    }
}
