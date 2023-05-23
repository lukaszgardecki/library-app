package com.example.libraryapp.domain.book.mapper;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;

public class BookToSaveDtoMapper {

    public static Book map(BookToSaveDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());
        book.setRelease_year(dto.getRelease_year());
        book.setPages(dto.getPages());
        book.setIsbn(dto.getIsbn());
        return book;
    }
}
