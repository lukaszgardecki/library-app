package com.example.authservice.core.authdetails;

import com.example.authservice.domain.exception.EmailAlreadyExistsException;
import com.example.authservice.domain.exception.UserAuthNotFoundException;
import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.AuthDetailsUpdate;
import com.example.authservice.domain.model.authdetails.values.*;
import com.example.authservice.domain.ports.out.AuthDetailsRepositoryPort;
import com.example.authservice.domain.ports.out.PasswordEncoderPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AuthDetailsService {
    private final AuthDetailsRepositoryPort userAuthRepository;
    private final PasswordEncoderPort passwordEncoder;

    AuthDetails getAuthDetailsById(AuthDetailsId authId) {
        return userAuthRepository.findById(authId).orElseThrow(() -> new UserAuthNotFoundException(authId));
    }

    AuthDetails getAuthDetailsByUserId(UserId userId) {
        return userAuthRepository.findByUserId(userId).orElseThrow(() -> new UserAuthNotFoundException(userId));
    }

    AuthDetails getAuthDetailsByEmail(Email email) {
        return userAuthRepository.findByEmail(email).orElseThrow(() -> new UserAuthNotFoundException(email));
    }

    void updateAuthDetails(UserId userId, AuthDetailsUpdate fieldsToUpdate) {
        AuthDetails authDetails = getAuthDetailsByUserId(userId);
        updateAuthDetailsModel(authDetails, fieldsToUpdate);
        userAuthRepository.save(authDetails);
    }

    void updateUserCredentials(UserId userId, Email email, Password password) {
        AuthDetails authDetails = getAuthDetailsByUserId(userId);
        updateCredentials(authDetails, email, password);
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

    private void updateAuthDetailsModel(AuthDetails authDetails, AuthDetailsUpdate fieldsToUpdate) {
        updateCredentials(authDetails, fieldsToUpdate.email(), fieldsToUpdate.psswrd());
        if (fieldsToUpdate.role() != null) authDetails.setRole(fieldsToUpdate.role());
        if (fieldsToUpdate.status() != null) authDetails.setStatus(fieldsToUpdate.status());
    }

    private void updateCredentials(AuthDetails authDetails, Email email, Password password) {
        if (email != null && !authDetails.getEmail().equals(email)) {
            validateEmail(email);
            authDetails.setEmail(email);
        }
        if (password != null) {
            authDetails.setPsswrd(new Password(passwordEncoder.encode(password.value())));
        }
    }
}
