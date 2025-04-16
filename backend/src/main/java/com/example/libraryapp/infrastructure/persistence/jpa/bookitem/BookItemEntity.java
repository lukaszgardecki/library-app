package com.example.libraryapp.infrastructure.persistence.jpa.bookitem;

import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
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
    private BookItemStatus status;

    private LocalDate dateOfPurchase;
    private Long bookId;
    private Long rackId;
    private Long shelfId;
}
