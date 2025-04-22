package com.example.libraryapp.core.fine;

import com.example.libraryapp.domain.fine.dto.FineCardDetailsDto;
import com.example.libraryapp.domain.fine.dto.FinePaymentResult;
import com.example.libraryapp.domain.event.types.fine.FinePaidEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.fine.model.FineId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class PayFineUseCase {
    private final FineService fineService;
    private final EventPublisherPort publisher;

    void execute(FineId fineId, FineCardDetailsDto cardDetails) {
        FinePaymentResult result = fineService.payFine(fineId, cardDetails);
        if (result.isSuccess()) publisher.publish(new FinePaidEvent(result.getUserId(), result.getAmount()));
    }
}
