package com.example.libraryapp.domain.book.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class BookDto extends RepresentationModel<BookDto> {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Integer release_year;
    private Integer pages;
    private String isbn;
    private Boolean availability;
}
