package com.example.authservice.core.authdetails;

import com.example.authservice.domain.dto.auth.CredentialsUpdateDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsUpdateDto;
import com.example.authservice.domain.exception.UserAuthNotFoundException;
import com.example.authservice.domain.exception.EmailAlreadyExistsException;
import com.example.authservice.domain.ports.PasswordEncoderPort;
import com.example.authservice.domain.ports.AuthDetailsRepositoryPort;
import com.example.authservice.domain.model.authdetails.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AuthDetailsService {
    private final AuthDetailsRepositoryPort userAuthRepository;
    private final PasswordEncoderPort passwordEncoder;

    AuthDetails getAuthDetailsById(AuthDetailsId authId) {
        return userAuthRepository.findById(authId).orElseThrow(UserAuthNotFoundException::new);
    }

    AuthDetails getAuthDetailsByUserId(UserId userId) {
        return userAuthRepository.findByUserId(userId).orElseThrow(() -> new UserAuthNotFoundException(userId));
    }

    AuthDetails getAuthDetailsByEmail(Email email) {
        return userAuthRepository.findByEmail(email).orElseThrow(() -> new UserAuthNotFoundException(email));
    }

    void updateAuthDetails(UserId userId, AuthDetailsUpdateDto fieldsToUpdate) {
        AuthDetails authDetails = getAuthDetailsByUserId(userId);
        updateAuthDetailsModel(authDetails, fieldsToUpdate);
        userAuthRepository.save(authDetails);
    }

    void updateUserCredentials(UserId userId, CredentialsUpdateDto fieldsToUpdate) {
        AuthDetails authDetails = getAuthDetailsByUserId(userId);
        updateCredentials(authDetails, fieldsToUpdate.username(), fieldsToUpdate.password());
        userAuthRepository.save(authDetails);
    }

    void save(Email username, Password password, UserId userId) {
        userAuthRepository.save(
                AuthDetails.builder()
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

    private void updateAuthDetailsModel(AuthDetails authDetails, AuthDetailsUpdateDto fieldsToUpdate) {
        updateCredentials(authDetails, fieldsToUpdate.username(), fieldsToUpdate.password());
        if (fieldsToUpdate.role() != null) authDetails.setRole(fieldsToUpdate.role());
        if (fieldsToUpdate.status() != null) authDetails.setStatus(fieldsToUpdate.status());
    }

    private void updateCredentials(AuthDetails authDetails, String username, String password) {
        if (username != null && !authDetails.getEmail().value().equals(username)) {
            Email email = new Email(username);
            validateEmail(email);
            authDetails.setEmail(email);
        }
        if (password != null) {
            authDetails.setPsswrd(new Password(passwordEncoder.encode(password)));
        }
    }
}
