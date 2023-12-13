package com.example.libraryapp.domain.book.dto;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto extends RepresentationModel<BookDto> {
    private Long id;
    private String title;
    private String subject;
    private String publisher;
    private String ISBN;
    private String language;
    private Integer pages;
    @JsonIgnore
    private List<BookItem> bookItems;
}
