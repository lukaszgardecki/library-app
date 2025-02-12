package com.example.libraryapp.application.fine;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
class FineCalculator {
    private static final BigDecimal DAILY_FINE = BigDecimal.valueOf(1.2);

    public static BigDecimal calculateFine(LocalDateTime returnDate, LocalDateTime dueDate) {
        long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        if (overdueDays <= 0) return BigDecimal.ZERO;
        return DAILY_FINE.multiply(BigDecimal.valueOf(overdueDays)).setScale(2, RoundingMode.HALF_UP);
    }
}
