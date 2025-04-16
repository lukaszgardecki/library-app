package com.example.libraryapp.adapter.http;

import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.user.dto.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class UserRegistrationController {
    private final UserFacade userFacade;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserDto request) {
        userFacade.registerNewUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fu")
    public ResponseEntity<Void> generateFakeUsers(@RequestParam int amount) {
        for (int i = 0; i < amount; i++) {
            userFacade.generateFakeUsers(amount);
        }
        return ResponseEntity.ok().build();
    }
}
