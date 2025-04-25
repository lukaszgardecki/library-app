package com.example.fineservice.infrastructure.integration.paymentservice;

import com.example.fineservice.domain.dto.PaymentDto;
import com.example.fineservice.domain.dto.PaymentProcessRequestDto;
import com.example.fineservice.domain.ports.PaymentServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PaymentServiceAdapter implements PaymentServicePort {
    private final PaymentServiceFeignClient client;

    @Override
    public PaymentDto processPayment(PaymentProcessRequestDto payment) {
        ResponseEntity<PaymentDto> response = client.processPayment(payment);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
