package com.example.fineservice.domain.event.incoming;

import com.example.fineservice.domain.dto.BookItemLoanDto;
import com.example.fineservice.domain.model.Price;
import lombok.Getter;

@Getter
public class BookItemLostEvent {
    private final BookItemLoanDto bookItemLoan;
    private final Price charge;

    public BookItemLostEvent(BookItemLoanDto bookItemLoan, Price charge) {
        this.bookItemLoan = bookItemLoan;
        this.charge = charge;
    }
}
