package com.example.libraryapp.domain.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private String title;
    private String author;
    private String publisher;
    private Integer release_year;
    private Integer pages;
    private String isbn;
}
