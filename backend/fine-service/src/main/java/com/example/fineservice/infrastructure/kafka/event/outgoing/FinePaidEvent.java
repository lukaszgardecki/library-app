package com.example.fineservice.infrastructure.kafka.event.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FinePaidEvent {
    private Long userId;
    private BigDecimal fineAmount;
}
