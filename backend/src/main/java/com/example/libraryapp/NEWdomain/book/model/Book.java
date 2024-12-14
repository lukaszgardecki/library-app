package com.example.libraryapp.NEWdomain.book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String subject;
    private String publisher;
    private String ISBN;
    private String language;
    private Integer pages;
}
