package com.example.activityservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemReturnedEvent {
    private Long loanId;
    private LocalDate loanDueDate;
    private LocalDate loanReturnDate;
    private Long userId;
    private Long bookItemId;
    private String bookTitle;
}
