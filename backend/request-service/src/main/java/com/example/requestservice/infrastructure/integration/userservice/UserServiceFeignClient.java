package com.example.requestservice.infrastructure.integration.userservice;

import com.example.requestservice.domain.integration.user.dto.PersonDto;
import com.example.requestservice.domain.integration.user.dto.UserDto;
import com.example.requestservice.infrastructure.integration.FeignClientCustomConfiguration;
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

    @GetMapping("/{userId}/verify/request")
    ResponseEntity<Void> verifyUserForBookItemRequest(@PathVariable Long userId);
}
