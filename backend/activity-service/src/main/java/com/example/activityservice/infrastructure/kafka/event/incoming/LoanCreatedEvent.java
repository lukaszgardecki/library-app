package com.example.activityservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanCreatedEvent {
    private Long loanId;
    private LocalDateTime loanCreationDate;
    private LocalDate loanDueDate;
    private LocalDateTime loanReturnDate;
    private Long userId;
    private Long bookItemId;
    private Long requestId;
    private Boolean isReferenceOnly;
    private String bookTitle;
    private String bookSubject;
}

