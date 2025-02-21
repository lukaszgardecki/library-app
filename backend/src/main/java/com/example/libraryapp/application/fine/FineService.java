package com.example.libraryapp.application.fine;

import com.example.libraryapp.application.payment.PaymentFacade;
import com.example.libraryapp.domain.fine.dto.FineCardDetailsDto;
import com.example.libraryapp.domain.fine.dto.FinePaymentResult;
import com.example.libraryapp.domain.fine.exceptions.FineAlreadyPaidException;
import com.example.libraryapp.domain.fine.exceptions.FineNotFoundException;
import com.example.libraryapp.domain.fine.model.Fine;
import com.example.libraryapp.domain.fine.model.FineStatus;
import com.example.libraryapp.domain.fine.ports.FineRepositoryPort;
import com.example.libraryapp.domain.payment.dto.PaymentCardDetailsDto;
import com.example.libraryapp.domain.payment.dto.PaymentProcessRequestDto;
import com.example.libraryapp.domain.payment.model.PaymentMethod;
import com.example.libraryapp.domain.payment.model.PaymentStatus;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
class FineService {
    private final FineRepositoryPort fineRepository;
    private final PaymentFacade paymentFacade;

    List<Fine> getAllByUserId(Long userId) {
        return fineRepository.findAllByUserId(userId);
    }

    Fine getFineById(Long id) {
        return fineRepository.findById(id)
                .orElseThrow(() -> new FineNotFoundException(id));
    }

    void processFineForBookReturn(LocalDateTime returnDate, LocalDateTime dueDate, Long userId, Long bookItemLoanId) {
        BigDecimal fine = FineCalculator.calculateFine(returnDate, dueDate);
        if (fine.compareTo(BigDecimal.ZERO) > 0) {
            Fine fineToSave = Fine.builder()
                    .amount(fine)
                    .userId(userId)
                    .loanId(bookItemLoanId)
                    .status(FineStatus.PENDING)
                    .build();
            fineRepository.save(fineToSave);
        }
    }

    void processFineForBookLost(
            LocalDateTime returnDate,
            LocalDateTime dueDate,
            Long userId,
            Long bookItemLoanId,
            BigDecimal bookItemPrice
    ) {
        BigDecimal fine = FineCalculator.calculateFine(returnDate, dueDate);

        Fine fineToSave = Fine.builder()
                .amount(fine.add(bookItemPrice))
                .userId(userId)
                .loanId(bookItemLoanId)
                .status(FineStatus.PENDING)
                .build();
        fineRepository.save(fineToSave);
    }

    FinePaymentResult payFine(Long fineId, FineCardDetailsDto cardDetails) {
        Fine fineToPay = getFineById(fineId);
        if (fineToPay.isPaid()) throw new FineAlreadyPaidException();

        PaymentProcessRequestDto payment = new PaymentProcessRequestDto(
                fineToPay.getAmount(),
                fineToPay.getUserId(),
                fineToPay.getDescription(),
                cardDetails != null ? PaymentMethod.CREDIT_CARD : PaymentMethod.CASH,
                cardDetails != null ? new PaymentCardDetailsDto(
                        cardDetails.getCardNumber(),
                        cardDetails.getCardHolderName(),
                        cardDetails.getExpirationDate(),
                        cardDetails.getCvv()
                ) : null
        );

        boolean paid = paymentFacade.processPayment(payment).getStatus() == PaymentStatus.SUCCESS;
        if (paid) fineRepository.setStatus(fineId, FineStatus.PAID);
        return new FinePaymentResult(fineToPay.getUserId(), fineToPay.getAmount(), paid);
    }
}
