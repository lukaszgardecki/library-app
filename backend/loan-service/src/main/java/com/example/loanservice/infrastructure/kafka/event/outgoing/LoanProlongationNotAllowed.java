package com.example.loanservice.infrastructure.kafka.event.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanProlongationNotAllowed {
    private Long bookItemId;
    private Long userId;
    private String bookTitle;
}
