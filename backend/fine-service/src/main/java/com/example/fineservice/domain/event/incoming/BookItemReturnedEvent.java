package com.example.fineservice.domain.event.incoming;

import com.example.fineservice.domain.dto.BookItemLoanDto;
import lombok.Getter;

@Getter
public class BookItemReturnedEvent {
    private final BookItemLoanDto bookItemLoan;

    public BookItemReturnedEvent(BookItemLoanDto bookItemLoan) {
        this.bookItemLoan = bookItemLoan;
    }
}
