package com.example.loanservice.infrastructure.integration.fineservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fine-service", path = "/api/v1/fines")
public interface FineServiceFeignClient {

    @GetMapping("/users/{userId}/validation")
    ResponseEntity<Void> verifyUserForFines(@PathVariable Long userId);
}
