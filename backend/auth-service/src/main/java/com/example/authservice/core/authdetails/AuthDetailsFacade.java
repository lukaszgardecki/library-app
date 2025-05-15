package com.example.authservice.core.authdetails;

import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.AuthDetailsUpdate;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.authdetails.values.UserId;
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

    public AuthDetails getAuthDetailsByEmail(Email username) {
        return getAuthDetailsUseCase.execute(username);
    }

    public AuthDetails getAuthDetailsByUserId(UserId userId) {
        return getAuthDetailsUseCase.execute(userId);
    }

    public void updateUserCredentials(UserId userId, Email email, Password password) {
        updateAuthDetailsUseCase.execute(userId, email, password);
    }

    public void updateAuthDetails(UserId userId, AuthDetailsUpdate fieldsToUpdate) {
        updateAuthDetailsUseCase.execute(userId, fieldsToUpdate);
    }

    public void validateEmail(Email username) {
        validateEmailUseCase.execute(username);
    }
}
