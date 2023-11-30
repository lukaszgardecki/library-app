package com.example.libraryapp.domain.book;

import com.example.libraryapp.domain.bookItem.BookItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subject;
    private String publisher;
    private String ISBN;
    private String language;
    private int pages;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookItem> bookItems;
    //    private List<Author> authors;

    public void addBookItem(BookItem item) {
        bookItems.add(item);
    }
}

