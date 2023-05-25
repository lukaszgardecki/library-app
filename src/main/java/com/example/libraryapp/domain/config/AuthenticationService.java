package com.example.libraryapp.domain.config;

import com.example.libraryapp.domain.user.UserService;
import com.example.libraryapp.domain.user.dto.UserLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    public void authenticateUser(UserLoginDto user) {
        Authentication newAuth = getNewAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    private Authentication getNewAuthentication(UserLoginDto user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails newUser = createUserDetails(user);
        return new UsernamePasswordAuthenticationToken(newUser,auth.getCredentials(),newUser.getAuthorities());
    }

    private UserDetails createUserDetails(UserLoginDto user) {
        String userRole = userService.findCredentialsByEmail(user.getUsername())
                .orElseThrow()
                .getRole();
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(userRole)
                .build();
    }
}