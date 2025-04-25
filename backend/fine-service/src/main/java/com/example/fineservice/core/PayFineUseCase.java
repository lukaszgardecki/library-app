package com.example.fineservice.core;

import com.example.fineservice.domain.dto.PaymentCardDetailsDto;
import com.example.fineservice.domain.dto.FinePaymentResult;
import com.example.fineservice.domain.model.FineId;
import com.example.fineservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class PayFineUseCase {
    private final FineService fineService;
    private final EventPublisherPort publisher;

    void execute(FineId fineId, PaymentCardDetailsDto cardDetails) {
        FinePaymentResult result = fineService.payFine(fineId, cardDetails);
        if (result.isSuccess()) publisher.publishFinePaidEvent(result.getUserId(), result.getAmount());
    }
}
