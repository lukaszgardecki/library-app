package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationRequest;
import com.example.libraryapp.domain.auth.AuthenticationResponse;
import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse savedUser = authService.register(request);
        URI savedUserUri = linkTo(methodOn(MemberController.class).getUserById(savedUser.getId())).toUri();
        return ResponseEntity.created(savedUserUri).body(savedUser);
    }

    // TODO: 30.11.2023 jak zrobić by weryfikować membera tez po ROLI?
    // TODO: 30.11.2023 czy te linki muszą tu być ? lepiej zrobić assembler
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticatedUser = authService.authenticate(request);
        URI authenticatedUserUri = linkTo(methodOn(MemberController.class).getUserById(authenticatedUser.getId())).toUri();
        return ResponseEntity.created(authenticatedUserUri).body(authenticatedUser);
    }
}
