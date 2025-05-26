package com.example.statisticsservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemReturnedEvent {
    private Long loanId;
    private LocalDate loanDueDate;
    private LocalDateTime loanReturnDate;
    private Long userId;
    private Long bookItemId;
    private String bookTitle;
}
