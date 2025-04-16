package com.example.libraryapp.domain.fine.dto;

import com.example.libraryapp.domain.fine.model.FineAmount;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class FinePaymentResult {
    private final UserId userId;
    private final FineAmount amount;
    private final boolean success;
}
