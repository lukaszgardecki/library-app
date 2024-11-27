package com.example.libraryapp.domain.book.mapper;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookPreviewDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;

import java.util.ArrayList;

public class BookMapper {

    public static BookDto map(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .subject(book.getSubject())
                .publisher(book.getPublisher())
                .ISBN(book.getISBN())
                .language(book.getLanguage())
                .pages(book.getPages())
                .bookItems(book.getBookItems())
                .build();
    }

    public static BookPreviewDto mapToPreviewDto(Book book) {
        return BookPreviewDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .subject(book.getSubject())
                .publisher(book.getPublisher())
                .build();
    }

    public static Book map(BookToSaveDto book) {
        return Book.builder()
                .title(book.getTitle())
                .subject(book.getSubject())
                .publisher(book.getPublisher())
                .ISBN(book.getISBN())
                .language(book.getLanguage())
                .pages(book.getPages())
                .build();
    }

    public static Book map(BookDto dto) {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .subject(dto.getSubject())
                .publisher(dto.getPublisher())
                .ISBN(dto.getISBN())
                .language(dto.getLanguage())
                .pages(dto.getPages())
                .bookItems(dto.getBookItems() != null ? dto.getBookItems() : new ArrayList<>())
                .build();
    }
}
