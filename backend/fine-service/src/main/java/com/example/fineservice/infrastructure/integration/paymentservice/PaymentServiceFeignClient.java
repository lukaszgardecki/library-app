package com.example.fineservice.infrastructure.integration.paymentservice;

import com.example.fineservice.domain.integration.payment.dto.PaymentDto;
import com.example.fineservice.domain.integration.payment.dto.PaymentProcessRequestDto;
import com.example.fineservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", path = "/payments", configuration = FeignClientCustomConfiguration.class)
public interface PaymentServiceFeignClient {

    @PostMapping("/process")
    ResponseEntity<PaymentDto> processPayment(@RequestBody PaymentProcessRequestDto payRequest);
}
