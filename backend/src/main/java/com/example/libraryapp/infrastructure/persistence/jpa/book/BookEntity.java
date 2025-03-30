package com.example.libraryapp.infrastructure.persistence.jpa.book;

import com.example.libraryapp.domain.book.model.BookFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "book")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subject;
    private String publisher;
    private String ISBN;
    private String language;
    private Integer pages;

    @Enumerated(EnumType.STRING)
    private BookFormat format;

    private LocalDate publicationDate;
}
