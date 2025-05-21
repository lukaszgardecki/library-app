package com.example.authservice.infrastructure.integration.userservice;

import com.example.authservice.infrastructure.integration.FeignClientCustomConfiguration;
import com.example.authservice.infrastructure.integration.userservice.dto.PersonDto;
import com.example.authservice.infrastructure.integration.userservice.dto.RegisterUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/users", configuration = FeignClientCustomConfiguration.class)
interface UserServiceFeignClient {

    @PostMapping("/register")
    ResponseEntity<Long> register(@RequestBody RegisterUserDto body);

    @GetMapping("/{userId}/person")
    ResponseEntity<PersonDto> getPersonByUserId(@PathVariable Long userId);

}
