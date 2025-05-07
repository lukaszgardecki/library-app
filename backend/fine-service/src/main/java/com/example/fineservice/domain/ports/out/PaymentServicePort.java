package com.example.fineservice.domain.ports.out;

import com.example.fineservice.domain.integration.payment.dto.PaymentDto;
import com.example.fineservice.domain.integration.payment.dto.PaymentProcessRequestDto;

public interface PaymentServicePort {

    PaymentDto processPayment(PaymentProcessRequestDto payment);
}
