package com.example.libraryapp.domain.book.dto;

import lombok.*;

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
}
