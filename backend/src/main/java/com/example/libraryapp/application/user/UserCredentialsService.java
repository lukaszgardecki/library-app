package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.auth.ports.PasswordEncoderPort;
import com.example.libraryapp.domain.user.exceptions.EmailAlreadyExistsException;
import com.example.libraryapp.domain.user.ports.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UserCredentialsService {
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;

    void validateEmail(String email) {
        boolean emailExists = userRepository.existsByEmail(email);
        if (emailExists) {
            throw new EmailAlreadyExistsException();
        }
    }

    String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
