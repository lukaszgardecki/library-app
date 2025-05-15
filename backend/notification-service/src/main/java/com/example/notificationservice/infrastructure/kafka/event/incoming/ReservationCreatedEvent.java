package com.example.notificationservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreatedEvent {
    private Long bookItemId;
    private Long userId;
    private int queue;
    private LocalDate loanDueDate;
    private String bookTitle;
}
