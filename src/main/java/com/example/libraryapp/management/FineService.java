package com.example.libraryapp.management;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class FineService {
    public static final BigDecimal DAY_FINE = new BigDecimal("0.2");
    public BigDecimal countFine(LocalDate dueDate, LocalDate now) {
        BigDecimal diffDays;
        if (now.isAfter(dueDate)) {
            diffDays = BigDecimal.valueOf(getDaysBetween(dueDate, now));
        } else {
            diffDays = BigDecimal.ZERO;
        }
        return diffDays.multiply(DAY_FINE);
    }

    private long getDaysBetween(LocalDate dueDate, LocalDate now) {
        return ChronoUnit.DAYS.between(dueDate, now);
    }

}
