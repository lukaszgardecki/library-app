package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.auth.ports.PasswordEncoderPort;
import com.example.libraryapp.domain.user.ports.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

@RequiredArgsConstructor
class UserCredentialsService {
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;

    void validateEmail(String email) {
        boolean emailExists = userRepository.existsByEmail(email);
        if (emailExists) {
            throw new BadCredentialsException("Message.VALIDATION_EMAIL_UNIQUE.getMessage()");
        }
    }

    String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
