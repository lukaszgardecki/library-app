package com.example.libraryapp.domain.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Integer release_year;
    private Integer pages;
    private String isbn;
}
