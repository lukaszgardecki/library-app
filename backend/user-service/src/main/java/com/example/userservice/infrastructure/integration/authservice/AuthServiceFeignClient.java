package com.example.userservice.infrastructure.integration.authservice;

import com.example.userservice.domain.dto.user.UserAuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", path = "/api/v1/auth")
interface AuthServiceFeignClient {

    @GetMapping("/user/{userId}")
    ResponseEntity<UserAuthDto> getUserAuthByUserId(@PathVariable Long userId);
}
