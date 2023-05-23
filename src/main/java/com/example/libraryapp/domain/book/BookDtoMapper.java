package com.example.libraryapp.domain.book;

public class BookDtoMapper {

    public static BookDto map(Book book) {
        BookDto dto = new BookDto();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setRelease_year(book.getRelease_year());
        dto.setPages(book.getPages());
        dto.setIsbn(book.getIsbn());
        return dto;
    }
}
