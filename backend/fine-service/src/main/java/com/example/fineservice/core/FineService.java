package com.example.fineservice.core;

import com.example.fineservice.domain.dto.FinePaymentResult;
import com.example.fineservice.domain.dto.PaymentCardDetailsDto;
import com.example.fineservice.domain.dto.PaymentProcessRequestDto;
import com.example.fineservice.domain.exceptions.FineAlreadyPaidException;
import com.example.fineservice.domain.exceptions.FineNotFoundException;
import com.example.fineservice.domain.model.*;
import com.example.fineservice.domain.ports.EventListenerPort;
import com.example.fineservice.domain.ports.FineRepositoryPort;
import com.example.fineservice.domain.ports.PaymentServicePort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

 @RequiredArgsConstructor
class FineService {
    private final FineRepositoryPort fineRepository;
    private final PaymentServicePort paymentService;

    List<Fine> getAllByUserId(UserId userId) {
        return fineRepository.findAllByUserId(userId);
    }

    Fine getFineById(FineId id) {
        return fineRepository.findById(id)
                .orElseThrow(() -> new FineNotFoundException(id));
    }

    FinePaymentResult payFine(FineId fineId, PaymentCardDetailsDto cardDetails) {
        Fine fineToPay = getFineById(fineId);
        if (fineToPay.isPaid()) throw new FineAlreadyPaidException();

        PaymentProcessRequestDto payment = new PaymentProcessRequestDto(
                fineToPay.getAmount().value(),
                fineToPay.getUserId().value(),
                fineToPay.getDescription().value(),
                cardDetails != null ? PaymentMethod.CREDIT_CARD : PaymentMethod.CASH,
                cardDetails != null ? new PaymentCardDetailsDto(
                        cardDetails.getCardNumber(),
                        cardDetails.getCardHolderName(),
                        cardDetails.getExpirationDate(),
                        cardDetails.getCvv()
                ) : null
        );

        boolean paid = paymentService.processPayment(payment).getStatus() == PaymentStatus.SUCCESS;
        if (paid) fineRepository.setStatus(fineId, FineStatus.PAID);
        return new FinePaymentResult(fineToPay.getUserId(), fineToPay.getAmount(), paid);
    }
}
