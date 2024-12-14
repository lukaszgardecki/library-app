package com.example.libraryapp.NEWdomain.book.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

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
