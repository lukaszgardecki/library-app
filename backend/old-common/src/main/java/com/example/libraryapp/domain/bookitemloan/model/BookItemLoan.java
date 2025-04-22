package com.example.libraryapp.domain.bookitemloan.model;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookItemLoan {
    private LoanId id;
    private LoanCreationDate creationDate;
    private LoanDueDate dueDate;
    private LoanReturnDate returnDate;
    private LoanStatus status;
    private UserId userId;
    private BookItemId bookItemId;
}
