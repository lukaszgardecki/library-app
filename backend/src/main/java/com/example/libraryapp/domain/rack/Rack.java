package com.example.libraryapp.domain.rack;

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
@Table(name = "rack")
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationIdentifier;

    @OneToMany(mappedBy = "rack", fetch = FetchType.LAZY)
    private List<BookItem> bookItems;

    public void addBookItem(BookItem item) {
        bookItems.add(item);
    }
}
