package com.example.libraryapp.NEWinfrastructure.persistence.jpa.book;

import jakarta.persistence.*;
import lombok.*;

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
}
