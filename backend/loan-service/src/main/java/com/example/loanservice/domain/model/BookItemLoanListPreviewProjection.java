package com.example.loanservice.domain.model;

import java.time.LocalDate;

public interface BookItemLoanListPreviewProjection {
    Long getId();
    LocalDate getCreationDate();
    LocalDate getDueDate();
    LoanStatus getStatus();
    Long getBookItemId();
    String getBookTitle();
}
