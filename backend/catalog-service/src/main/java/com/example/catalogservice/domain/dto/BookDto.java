package com.example.catalogservice.domain.dto;

import com.example.catalogservice.domain.model.book.values.BookFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String subject;
    private String publisher;
    private String ISBN;
    private String language;
    private Integer pages;
    private BookFormat format;
    private LocalDate publicationDate;
}
