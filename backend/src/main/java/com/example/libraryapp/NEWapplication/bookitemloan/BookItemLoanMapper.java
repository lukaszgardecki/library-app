package com.example.libraryapp.NEWapplication.bookitemloan;

import com.example.libraryapp.NEWdomain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoan;

class BookItemLoanMapper {

    static BookItemLoan toModel(BookItemLoanDto dto) {
        return BookItemLoan.builder()
                .id(dto.id())
                .creationDate(dto.creationDate())
                .dueDate(dto.dueDate())
                .returnDate(dto.returnDate())
                .status(dto.status())
                .userId(dto.userId())
                .bookId(dto.bookId())
                .bookItemId(dto.bookItemId())
                .build();
    }

    static BookItemLoanDto toDto(BookItemLoan model) {
        return new BookItemLoanDto(
                model.getId(),
                model.getCreationDate(),
                model.getDueDate(),
                model.getReturnDate(),
                model.getStatus(),
                model.getUserId(),
                model.getBookId(),
                model.getBookItemId()
        );
    }
}
