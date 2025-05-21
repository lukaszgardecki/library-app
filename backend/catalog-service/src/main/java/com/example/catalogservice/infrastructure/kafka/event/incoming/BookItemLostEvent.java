package com.example.catalogservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemLostEvent {
    private Long loanId;
    private LocalDate loanDueDate;
    private LocalDateTime loanReturnDate;
    private Long userId;
    private Long bookItemId;
    private Long bookId;
    private BigDecimal charge;
    private String bookTitle;
}
