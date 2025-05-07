package com.example.fineservice.domain.dto;

import com.example.fineservice.domain.model.values.FineAmount;
import com.example.fineservice.domain.model.values.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FinePaymentResult {
    private final UserId userId;
    private final FineAmount amount;
    private final boolean success;
}
