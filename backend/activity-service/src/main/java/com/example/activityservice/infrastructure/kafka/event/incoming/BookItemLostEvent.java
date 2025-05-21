package com.example.activityservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemLostEvent {
    private Long loanId;
    private LocalDate loanDueDate;
    private LocalDate loanReturnDate;
    private Long userId;
    private Long bookItemId;
    private Long bookId;
    private BigDecimal charge;
    private String bookTitle;
}
