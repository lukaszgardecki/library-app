package com.example.authservice.infrastructure.http;

import com.example.authservice.core.registration.RegistrationFacade;
import com.example.authservice.domain.dto.auth.RegisterToSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
class RegisterController {
    private final RegistrationFacade registrationFacade;

    @PostMapping
    ResponseEntity<Void> register(@RequestBody RegisterToSaveDto request) {
        registrationFacade.registerUser(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/fu")
    ResponseEntity<Void> generateFakeUsers(@RequestParam int amount) {
        registrationFacade.generateFakeUsers(amount);
        return ResponseEntity.noContent().build();
    }
}
