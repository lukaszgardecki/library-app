package com.example.warehouseservice.infrastructure.integration.userservice;

import com.example.warehouseservice.domain.dto.PersonDto;
import com.example.warehouseservice.domain.dto.UserDto;
import com.example.warehouseservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/users", configuration = FeignClientCustomConfiguration.class)
interface UserServiceFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable Long id);

    @GetMapping("/{userId}/person")
    ResponseEntity<PersonDto> getPersonByUserId(@PathVariable Long userId);
}
