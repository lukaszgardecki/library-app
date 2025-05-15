package com.example.loanservice.domain.integration.catalogservice.book;

import com.example.loanservice.domain.integration.catalogservice.book.values.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Book {
    private BookId id;
    private Title title;
    private Subject subject;
    private Publisher publisher;
    private Isbn ISBN;
    private Language language;
    private Pages pages;
    private BookFormat format;
    private PublicationDate publicationDate;
}
