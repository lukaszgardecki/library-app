package com.example.libraryapp.domain.bookitemloan.dto;

import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookItemLoanListPreviewDto {
    private Long id;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private LoanStatus status;
    private Long bookItemId;
    private String bookTitle;
}
