package com.example.fineservice.infrastructure.integration.paymentservice;

import com.example.fineservice.domain.dto.PaymentDto;
import com.example.fineservice.domain.dto.PaymentProcessRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", path = "/api/v1/payments")
public interface PaymentServiceFeignClient {

    @PostMapping("/process")
    ResponseEntity<PaymentDto> processPayment(@RequestBody PaymentProcessRequestDto payRequest);
}
