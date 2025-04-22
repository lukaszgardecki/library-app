package com.example.userservice.user.infrastructure.integration.fineservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fine", path = "/api/v1/fines")
interface FineServiceFeignClient {

    @GetMapping("/users/{userId}/validation")
    ResponseEntity<Void> validateUserForFines(@PathVariable Long userId);
}
