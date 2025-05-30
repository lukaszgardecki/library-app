package com.example.fineservice.infrastructure.integration.paymentservice;

import com.example.fineservice.domain.integration.payment.dto.PaymentDto;
import com.example.fineservice.domain.integration.payment.dto.PaymentProcessRequestDto;
import com.example.fineservice.domain.ports.out.PaymentServicePort;
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
