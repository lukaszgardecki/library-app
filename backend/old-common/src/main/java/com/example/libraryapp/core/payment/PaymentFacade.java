package com.example.libraryapp.core.payment;

import com.example.libraryapp.domain.payment.dto.PaymentDto;
import com.example.libraryapp.domain.payment.dto.PaymentProcessRequestDto;
import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.payment.model.PaymentId;
import com.example.libraryapp.domain.payment.model.PaymentProcessRequest;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PaymentFacade {
    private final ProcessPaymentUseCase processPaymentUseCase;
    private final GetPaymentUseCase getPaymentUseCase;
    private final GetAllUserPaymentsUseCase getAllUserPaymentsUseCase;

    public Page<PaymentDto> getAllByUserId(UserId userId, Pageable pageable) {
        return getAllUserPaymentsUseCase.execute(userId, pageable)
                .map(PaymentMapper::toDto);
    }

    public PaymentDto getPayment(PaymentId id) {
        Payment payment = getPaymentUseCase.execute(id);
        return PaymentMapper.toDto(payment);
    }

    public PaymentDto processPayment(PaymentProcessRequestDto requestDto) {
        PaymentProcessRequest paymentRequest = PaymentMapper.toModel(requestDto);
        Payment payment = processPaymentUseCase.execute(paymentRequest);
        return PaymentMapper.toDto(payment);
    }
}
