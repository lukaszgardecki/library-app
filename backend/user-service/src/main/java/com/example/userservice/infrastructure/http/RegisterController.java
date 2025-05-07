package com.example.userservice.infrastructure.http;

import com.example.userservice.core.user.UserFacade;
import com.example.userservice.domain.dto.user.RegisterUserDto;
import com.example.userservice.domain.model.user.values.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/register")
@RequiredArgsConstructor
class RegisterController {
    private final UserFacade userFacade;

    @PostMapping
    ResponseEntity<Long> register(@RequestBody RegisterUserDto body) {
        UserId userId = userFacade.createNewUser(body);
        return ResponseEntity.ok(userId.value());
    }
}
