package com.example.libraryapp.web;

import com.example.libraryapp.domain.user.UserService;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserRegistrationDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto registrationDto) {
        UserDto savedUser;
        try {
            savedUser = userService.registerUserWithDefaultRole(registrationDto);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Reason", "User with this email already exists")
                    .build();
        }
        URI savedUserUri = linkTo(methodOn(UserController.class).getUserById(savedUser.getId())).toUri();
        return ResponseEntity.created(savedUserUri).body(savedUser);
    }
}
