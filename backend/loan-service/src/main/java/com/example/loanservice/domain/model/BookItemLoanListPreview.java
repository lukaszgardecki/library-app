package com.example.loanservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookItemLoanListPreview implements BookItemLoanListPreviewProjection {
    private Long id;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private LoanStatus status;
    private Long bookItemId;
    private String bookTitle;
}
