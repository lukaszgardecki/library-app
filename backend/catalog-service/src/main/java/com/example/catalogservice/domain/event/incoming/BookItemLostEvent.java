package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.model.LoanId;
import com.example.catalogservice.domain.model.LoanReturnDate;
import com.example.catalogservice.domain.model.UserId;
import com.example.catalogservice.domain.model.book.BookId;
import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.LoanDueDate;
import com.example.catalogservice.domain.model.bookitem.Price;
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
