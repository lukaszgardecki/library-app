package com.example.loanservice.domain.dto;

import com.example.loanservice.domain.model.values.LoanStatus;
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
