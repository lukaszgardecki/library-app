package com.example.userservice.infrastructure.integration.bookitemloanservice;

import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.integration.loan.BookItemLoan;
import com.example.userservice.domain.integration.loan.values.*;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.infrastructure.integration.bookitemloanservice.dto.BookItemLoanDto;

class BookItemLoanMapper {

    static BookItemLoan toModel(BookItemLoanDto dto) {
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

    static BookItemLoanDto toDto(BookItemLoan model) {
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
