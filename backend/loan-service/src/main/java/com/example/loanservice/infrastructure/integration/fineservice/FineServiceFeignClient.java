package com.example.loanservice.infrastructure.integration.fineservice;

import com.example.loanservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fine-service", path = "/fines", configuration = FeignClientCustomConfiguration.class)
interface FineServiceFeignClient {

    @GetMapping("/users/{userId}/validation")
    ResponseEntity<Void> verifyUserForFines(@PathVariable Long userId);
}
