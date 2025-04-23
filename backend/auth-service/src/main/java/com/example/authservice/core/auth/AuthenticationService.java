package com.example.authservice.core.auth;

import com.example.authservice.domain.dto.auth.CredentialsUpdateDto;
import com.example.authservice.domain.dto.auth.UserAuthUpdateDto;
import com.example.authservice.domain.exception.UserAuthNotFoundException;
import com.example.authservice.domain.exception.EmailAlreadyExistsException;
import com.example.authservice.domain.ports.PasswordEncoderPort;
import com.example.authservice.domain.ports.UserAuthRepositoryPort;
import com.example.authservice.domain.model.auth.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AuthenticationService {
    private final UserAuthRepositoryPort userAuthRepository;
    private final PasswordEncoderPort passwordEncoder;

    UserAuth getUserAuthByUserId(UserId userId) {
        return userAuthRepository.findByUserId(userId).orElseThrow(UserAuthNotFoundException::new);
    }

    void updateUserAuth(UserId userId, UserAuthUpdateDto fieldsToUpdate) {
        UserAuth userAuth = getUserAuthByUserId(userId);
        updateUserAuthModel(userAuth, fieldsToUpdate);
        userAuthRepository.save(userAuth);
    }

    void updateUserCredentials(UserId userId, CredentialsUpdateDto fieldsToUpdate) {
        UserAuth userAuth = getUserAuthByUserId(userId);
        updateCredentials(userAuth, fieldsToUpdate.username(), fieldsToUpdate.password());
        userAuthRepository.save(userAuth);
    }

    void save(Email username, Password password, UserId userId) {
        userAuthRepository.save(
                UserAuth.builder()
                        .email(username)
                        .psswrd(new Password(passwordEncoder.encode(password.value())))
                        .userId(userId)
                        .role(Role.USER)
                        .status(AccountStatus.PENDING)
                        .build()
        );
    }

    void validateEmail(Email email) {
        boolean emailExists = userAuthRepository.existsByEmail(email);
        if (emailExists) {
            throw new EmailAlreadyExistsException();
        }
    }

    String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    UserAuth getUserAuthByEmail(Email email) {
        return userAuthRepository.findByEmail(email).orElseThrow(UserAuthNotFoundException::new);
    }

    private void updateUserAuthModel(UserAuth userAuth, UserAuthUpdateDto fieldsToUpdate) {
        updateCredentials(userAuth, fieldsToUpdate.username(), fieldsToUpdate.password());
        if (fieldsToUpdate.role() != null) userAuth.setRole(fieldsToUpdate.role());
        if (fieldsToUpdate.status() != null) userAuth.setStatus(fieldsToUpdate.status());
    }

    private void updateCredentials(UserAuth userAuth, String username, String password) {
        if (username != null && !userAuth.getEmail().value().equals(username)) {
            Email email = new Email(username);
            validateEmail(email);
            userAuth.setEmail(email);
        }
        if (password != null) {
            userAuth.setPsswrd(new Password(passwordEncoder.encode(password)));
        }
    }
}
