package com.example.libraryapp.domain.bookItem;

import com.example.libraryapp.domain.book.Book;
import com.example.libraryapp.domain.rack.Rack;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "book_item")
public class BookItem {
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

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonManagedReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "rack_id")
    private Rack rack;

    public void updateAfterReservation() {
        if (status == BookItemStatus.AVAILABLE) status = BookItemStatus.RESERVED;
    }
    public void updateAfterReservationCancelling(boolean isBookReserved) {
        boolean bookIsNotReserved = !isBookReserved;
        if (bookIsNotReserved) {
            this.status = BookItemStatus.AVAILABLE;
        }
    }

    public void updateAfterLending(LocalDate borrowedDate, LocalDate dueDate) {
        this.borrowed = borrowedDate;
        this.dueDate = dueDate;
        this.status = BookItemStatus.LOANED;
    }

    public void updateAfterReturning(LocalDate returnDate, boolean bookIsReserved) {
        if (bookIsReserved) {
            this.status = BookItemStatus.RESERVED;
        } else {
            this.status = BookItemStatus.AVAILABLE;
        }
        this.dueDate = returnDate;
    }
}
