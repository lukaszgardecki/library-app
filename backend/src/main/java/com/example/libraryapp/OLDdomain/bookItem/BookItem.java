package com.example.libraryapp.OLDdomain.bookItem;

import com.example.libraryapp.NEWdomain.bookItem.model.BookItemFormat;
import com.example.libraryapp.NEWdomain.bookItem.model.BookItemStatus;
import com.example.libraryapp.OLDdomain.book.Book;
import com.example.libraryapp.OLDdomain.rack.Rack;
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


    public void updateAfterReservationCancelling(boolean isBookReserved) {
        boolean bookIsNotReserved = !isBookReserved;
        if (bookIsNotReserved) {
            this.status = BookItemStatus.AVAILABLE;
        }
    }


    public void updateAfterReturning(LocalDate returnDate, boolean bookIsReserved) {
        if (bookIsReserved) {
            this.status = BookItemStatus.REQUESTED;
        } else {
            this.status = BookItemStatus.AVAILABLE;
        }
        this.dueDate = returnDate;
    }
}
