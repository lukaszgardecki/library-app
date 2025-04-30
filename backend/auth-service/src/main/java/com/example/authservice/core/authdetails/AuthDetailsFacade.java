package com.example.authservice.core.authdetails;

import com.example.authservice.domain.dto.auth.CredentialsToSaveDto;
import com.example.authservice.domain.dto.auth.CredentialsUpdateDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsUpdateDto;
import com.example.authservice.domain.model.authdetails.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthDetailsFacade {
    private final GetAuthDetailsUseCase getAuthDetailsUseCase;
    private final CreateAuthDetailsUseCase createAuthDetailsUseCase;
    private final UpdateAuthDetailsUseCase updateAuthDetailsUseCase;

    public AuthDetailsDto getAuthDetailsByEmail(Email username) {
        AuthDetails authDetails = getAuthDetailsUseCase.execute(username);
        return AuthDetailsMapper.toDto(authDetails);
    }

    public AuthDetailsDto getAuthDetailsByUserId(UserId userId) {
        AuthDetails authDetails = getAuthDetailsUseCase.execute(userId);
        return AuthDetailsMapper.toDto(authDetails);
    }

    public void createAuthDetails(CredentialsToSaveDto request) {
        createAuthDetailsUseCase.execute(
                new Email(request.username()),
                new Password(request.password()),
                new UserId(request.userId())
        );
    }

    public void updateAuthDetails(UserId userId, CredentialsUpdateDto fieldsToUpdate) {
        updateAuthDetailsUseCase.execute(userId, fieldsToUpdate);
    }

    public void updateAuthDetails(UserId userId, AuthDetailsUpdateDto fieldsToUpdate) {
        updateAuthDetailsUseCase.execute(userId, fieldsToUpdate);
    }
}
