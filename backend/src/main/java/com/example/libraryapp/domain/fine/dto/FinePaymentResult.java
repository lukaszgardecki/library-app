package com.example.libraryapp.domain.fine.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class FinePaymentResult {
    private final Long userId;
    private final BigDecimal amount;
    private final boolean success;
}
