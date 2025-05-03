package com.example.loanservice.infrastructure.integration.userservice;

import com.example.loanservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/users", configuration = FeignClientCustomConfiguration.class)
interface UserServiceFeignClient {

    @GetMapping("/{userId}/verify/loan")
    ResponseEntity<Void> verifyUserForBookItemLoan(@PathVariable Long userId);

    @GetMapping("/{userId}/verify/renewal")
    ResponseEntity<Void> verifyUserForBookItemRenewal(@PathVariable Long userId);
}
