package com.example.authservice.core.authdetails;

import com.example.authservice.domain.dto.auth.CredentialsUpdateDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsUpdateDto;
import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.model.authdetails.Password;
import com.example.authservice.domain.model.authdetails.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthDetailsFacade {
    private final CreateNewAuthDetailsUseCase createNewAuthDetailsUseCase;
    private final GetAuthDetailsUseCase getAuthDetailsUseCase;
    private final UpdateAuthDetailsUseCase updateAuthDetailsUseCase;
    private final ValidateEmailUseCase validateEmailUseCase;

    public void createNewAuthDetails(Email email, Password password, UserId userId) {
        createNewAuthDetailsUseCase.execute(email, password, userId);
    }

    public AuthDetailsDto getAuthDetailsByEmail(Email username) {
        AuthDetails authDetails = getAuthDetailsUseCase.execute(username);
        return AuthDetailsMapper.toDto(authDetails);
    }

    public AuthDetailsDto getAuthDetailsByUserId(UserId userId) {
        AuthDetails authDetails = getAuthDetailsUseCase.execute(userId);
        return AuthDetailsMapper.toDto(authDetails);
    }

    public void updateAuthDetails(UserId userId, CredentialsUpdateDto fieldsToUpdate) {
        updateAuthDetailsUseCase.execute(userId, fieldsToUpdate);
    }

    public void updateAuthDetails(UserId userId, AuthDetailsUpdateDto fieldsToUpdate) {
        updateAuthDetailsUseCase.execute(userId, fieldsToUpdate);
    }

    public void validateEmail(Email username) {
        validateEmailUseCase.execute(username);
    }
}
