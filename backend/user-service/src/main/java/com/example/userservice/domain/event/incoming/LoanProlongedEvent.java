package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.model.book.Title;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.bookitemloan.LoanDueDate;
import com.example.userservice.domain.model.bookitemloan.LoanId;
import com.example.userservice.domain.model.bookitemloan.LoanReturnDate;
import com.example.userservice.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanProlongedEvent {
    private LoanId loanId;
    private LoanDueDate loanDueDate;
    private LoanReturnDate loanReturnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private Title bookTitle;
}
