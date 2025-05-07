package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.integration.loan.LoanId;
import com.example.catalogservice.domain.integration.loan.LoanReturnDate;
import com.example.catalogservice.domain.integration.UserId;
import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.book.values.Title;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.LoanDueDate;
import com.example.catalogservice.domain.model.bookitem.values.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemLostEvent {
    private LoanId loanId;
    private LoanDueDate loanDueDate;
    private LoanReturnDate loanReturnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private BookId bookId;
    private Price charge;
    private Title bookTitle;
}
