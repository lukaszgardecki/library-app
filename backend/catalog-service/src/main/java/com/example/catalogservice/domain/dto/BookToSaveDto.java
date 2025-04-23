package com.example.catalogservice.domain.dto;

import com.example.catalogservice.domain.model.book.BookFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookToSaveDto {
    private String title;
    private String subject;
    private String publisher;
    private String ISBN;
    private String language;
    private Integer pages;
    private BookFormat format;
    private LocalDate publicationDate;
}
