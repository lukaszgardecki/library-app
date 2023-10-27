package com.example.libraryapp.domain.book;

import com.example.libraryapp.domain.lending.Lending;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Integer release_year;
    private Integer pages;
    private String isbn;
    private Boolean availability;
    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<Lending> lendings;
}
