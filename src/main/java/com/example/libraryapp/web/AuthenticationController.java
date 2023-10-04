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
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse savedUser = service.register(request);
        URI savedUserUri = linkTo(methodOn(UserController.class).getUserById(savedUser.getId())).toUri();
        return ResponseEntity.created(savedUserUri).body(savedUser);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticatedUser = service.authenticate(request);
        URI authenticatedUserUri = linkTo(methodOn(UserController.class).getUserById(authenticatedUser.getId())).toUri();
        return ResponseEntity.created(authenticatedUserUri).body(authenticatedUser);
    }
}
