package com.example.libraryapp.web;

import com.example.libraryapp.domain.config.assembler.UserModelAssembler;
import com.example.libraryapp.domain.user.UserService;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserLoginDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          UserModelAssembler userModelAssembler) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<UserDto>> authenticateUser(@RequestBody UserLoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            return ResponseEntity.notFound().build();
        }
        return userService.findAllUsers().get().stream()
                .filter(u -> u.getEmail().equals(loginDto.getUsername()))
                .map(userModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .findFirst().orElseThrow();
    }
}
