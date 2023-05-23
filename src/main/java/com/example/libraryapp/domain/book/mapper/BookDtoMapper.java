package com.example.libraryapp.domain.book.mapper;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.dto.BookDto;

public class BookDtoMapper {

    public static BookDto map(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setRelease_year(book.getRelease_year());
        dto.setPages(book.getPages());
        dto.setIsbn(book.getIsbn());
        return dto;
    }

}
