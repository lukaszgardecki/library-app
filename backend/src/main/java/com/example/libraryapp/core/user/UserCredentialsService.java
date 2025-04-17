package com.example.libraryapp.core.user;

import com.example.libraryapp.domain.auth.ports.PasswordEncoderPort;
import com.example.libraryapp.domain.user.exceptions.EmailAlreadyExistsException;
import com.example.libraryapp.domain.user.model.Email;
import com.example.libraryapp.domain.user.ports.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UserCredentialsService {
    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;

    void validateEmail(Email email) {
        boolean emailExists = userRepository.existsByEmail(email);
        if (emailExists) {
            throw new EmailAlreadyExistsException();
        }
    }

    String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
