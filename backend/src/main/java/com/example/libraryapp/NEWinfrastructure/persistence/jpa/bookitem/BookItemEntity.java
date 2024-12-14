package com.example.libraryapp.NEWinfrastructure.persistence.jpa.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItemFormat;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "book_item")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class BookItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String barcode;
    private Boolean isReferenceOnly;
    private LocalDate borrowed;
    private LocalDate dueDate;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private BookItemFormat format;

    @Enumerated(EnumType.STRING)
    private BookItemStatus status;

    private LocalDate dateOfPurchase;
    private LocalDate publicationDate;
    private Long bookId;
    private Long rackId;
}
