package com.example.libraryapp.core.fine;

import com.example.libraryapp.core.payment.PaymentFacade;
import com.example.libraryapp.domain.bookitem.model.Price;
import com.example.libraryapp.domain.bookitemloan.model.LoanId;
import com.example.libraryapp.domain.fine.dto.FineCardDetailsDto;
import com.example.libraryapp.domain.fine.dto.FinePaymentResult;
import com.example.libraryapp.domain.fine.exceptions.FineAlreadyPaidException;
import com.example.libraryapp.domain.fine.exceptions.FineNotFoundException;
import com.example.libraryapp.domain.fine.model.Fine;
import com.example.libraryapp.domain.fine.model.FineAmount;
import com.example.libraryapp.domain.fine.model.FineId;
import com.example.libraryapp.domain.fine.model.FineStatus;
import com.example.libraryapp.domain.fine.ports.FineRepositoryPort;
import com.example.libraryapp.domain.payment.dto.PaymentCardDetailsDto;
import com.example.libraryapp.domain.payment.dto.PaymentProcessRequestDto;
import com.example.libraryapp.domain.payment.model.PaymentMethod;
import com.example.libraryapp.domain.payment.model.PaymentStatus;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
class FineService {
    private final FineRepositoryPort fineRepository;
    private final PaymentFacade paymentFacade;

    List<Fine> getAllByUserId(UserId userId) {
        return fineRepository.findAllByUserId(userId);
    }

    Fine getFineById(FineId id) {
        return fineRepository.findById(id)
                .orElseThrow(() -> new FineNotFoundException(id));
    }

    void processFineForBookReturn(LocalDateTime returnDate, LocalDateTime dueDate, UserId userId, LoanId bookItemLoanId) {
        BigDecimal amount = FineCalculator.calculateFine(returnDate, dueDate);
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            Fine fineToSave = Fine.builder()
                    .amount(new FineAmount(amount))
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
            UserId userId,
            LoanId bookItemLoanId,
            Price bookItemPrice
    ) {
        BigDecimal fine = FineCalculator.calculateFine(returnDate, dueDate);

        Fine fineToSave = Fine.builder()
                .amount(new FineAmount(fine.add(bookItemPrice.value())))
                .userId(userId)
                .loanId(bookItemLoanId)
                .status(FineStatus.PENDING)
                .build();
        fineRepository.save(fineToSave);
    }

    FinePaymentResult payFine(FineId fineId, FineCardDetailsDto cardDetails) {
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

        boolean paid = paymentFacade.processPayment(payment).getStatus() == PaymentStatus.SUCCESS;
        if (paid) fineRepository.setStatus(fineId, FineStatus.PAID);
        return new FinePaymentResult(fineToPay.getUserId(), fineToPay.getAmount(), paid);
    }
}
