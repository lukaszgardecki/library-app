package com.example.loanservice.infrastructure.http;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.*;
import com.example.loanservice.infrastructure.http.dto.BookItemLoanDto;

public class BookItemLoanMapper {

    public static BookItemLoan toModel(BookItemLoanDto dto) {
        return BookItemLoan.builder()
                .id(new LoanId(dto.id()))
                .creationDate(new LoanCreationDate(dto.creationDate()))
                .dueDate(new LoanDueDate(dto.dueDate()))
                .returnDate(new LoanReturnDate(dto.returnDate()))
                .status(LoanStatus.valueOf(dto.status()))
                .userId(new UserId(dto.userId()))
                .bookItemId(new BookItemId(dto.bookItemId()))
                .build();
    }

    public static BookItemLoanDto toDto(BookItemLoan model) {
        return new BookItemLoanDto(
                model.getId().value(),
                model.getCreationDate().value(),
                model.getDueDate().value(),
                model.getReturnDate().value(),
                model.getStatus().name(),
                model.getUserId().value(),
                model.getBookItemId().value()
        );
    }
}
