package com.example.libraryapp.domain.bookitemloan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookItemLoan {
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private BookItemLoanStatus status;
    private Long userId;
    private Long bookId;
    private Long bookItemId;
}
