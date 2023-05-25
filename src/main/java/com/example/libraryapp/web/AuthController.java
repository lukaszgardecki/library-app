package com.example.libraryapp.web;

import com.example.libraryapp.domain.config.AuthenticationService;
import com.example.libraryapp.domain.user.dto.UserLoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserLoginDto loginDto) {
        try {
            authenticationService.authenticateUser(loginDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Reason", "User with this email does not exist")
                    .body("Wrong email");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User signed-in successfully!");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok()
                .body("You've been signed out!");
    }
}
