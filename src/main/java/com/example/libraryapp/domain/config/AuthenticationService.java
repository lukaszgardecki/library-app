package com.example.libraryapp.domain.config;

import com.example.libraryapp.domain.user.UserService;
import com.example.libraryapp.domain.user.dto.UserCredentialsDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserLoginDto;
import com.example.libraryapp.domain.user.mapper.UserDtoMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    public Optional<UserDto> authenticateUser(UserLoginDto user) {
        UserCredentialsDto foundCredentials = userService.findCredentialsByEmail(user.getUsername())
                .orElseThrow();

        Authentication newAuth = getNewAuthentication(foundCredentials);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return userService.findUserById(foundCredentials.getUserId());
    }

    private Authentication getNewAuthentication(UserCredentialsDto credentials) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails newUser = createUserDetails(credentials);
        return new UsernamePasswordAuthenticationToken(newUser,auth.getCredentials(),newUser.getAuthorities());
    }

    private UserDetails createUserDetails(UserCredentialsDto credentials) {
        return User.builder()
                .username(credentials.getEmail())
                .password(credentials.getPassword())
                .roles(credentials.getRole())
                .build();
    }
}