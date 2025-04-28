package com.example.requestservice.infrastructure.integration.userservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/users")
public interface UserServiceFeignClient {

    @GetMapping("/{userId}/verify/request")
    ResponseEntity<Void> verifyUserForBookItemRequest(@PathVariable Long userId);
}
