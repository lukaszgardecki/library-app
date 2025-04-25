package com.example.fineservice.domain.ports;

import com.example.fineservice.domain.dto.PaymentDto;
import com.example.fineservice.domain.dto.PaymentProcessRequestDto;

public interface PaymentServicePort {

    PaymentDto processPayment(PaymentProcessRequestDto payment);
}
