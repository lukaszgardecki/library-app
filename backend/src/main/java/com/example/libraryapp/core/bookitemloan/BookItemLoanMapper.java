package com.example.libraryapp.core.bookitemloan;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanListPreviewDto;
import com.example.libraryapp.domain.bookitemloan.model.*;
import com.example.libraryapp.domain.user.model.UserId;

class BookItemLoanMapper {

    static BookItemLoan toModel(BookItemLoanDto dto) {
        return BookItemLoan.builder()
                .id(new LoanId(dto.id()))
                .creationDate(new LoanCreationDate(dto.creationDate()))
                .dueDate(new LoanDueDate(dto.dueDate()))
                .returnDate(new LoanReturnDate(dto.returnDate()))
                .status(dto.status())
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
                model.getStatus(),
                model.getUserId().value(),
                model.getBookItemId().value()
        );
    }

    static BookItemLoanListPreviewDto toDto(BookItemLoanListPreviewProjection model) {
        return new BookItemLoanListPreviewDto(
                model.getId(),
                model.getCreationDate(),
                model.getDueDate(),
                model.getStatus(),
                model.getBookItemId(),
                model.getBookTitle()
        );
    }
}
