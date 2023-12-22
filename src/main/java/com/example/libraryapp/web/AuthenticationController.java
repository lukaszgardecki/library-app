package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.auth.LoginRequest;
import com.example.libraryapp.domain.auth.LoginResponse;
import com.example.libraryapp.domain.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        LoginResponse token = authService.register(request);
        return ResponseEntity.ok(token);
    }

    // TODO: 30.11.2023 jak zrobić by weryfikować membera tez po ROLI?
    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {
        LoginResponse token = authService.authenticate(request);
        return ResponseEntity.ok(token);
    }
}
