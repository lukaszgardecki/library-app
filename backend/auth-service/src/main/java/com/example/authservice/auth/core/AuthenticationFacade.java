package com.example.authservice.auth.core;

import com.example.authservice.auth.domain.dto.CredentialsToSaveDto;
import com.example.authservice.auth.domain.dto.CredentialsUpdateDto;
import com.example.authservice.auth.domain.dto.UserAuthDto;
import com.example.authservice.auth.domain.dto.UserAuthUpdateDto;
import com.example.authservice.auth.domain.exceptions.ForbiddenAccessException;
import com.example.authservice.auth.domain.model.*;
import com.example.authservice.token.domain.dto.TokenAuth;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AuthenticationFacade {
    private final GetUserAuthUseCase getUserAuthUseCase;
    private final SaveUserCredentialsUseCase saveUserCredentialsUseCase;
    private final UpdateUserAuthUseCase updateUserAuthUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final GetCurrentLoggedInUserUseCase getCurrentLoggedInUserUseCase;

    public TokenAuth authenticate(Email username, Password password) {
        return authenticateUserUseCase.execute(username, password);
    }

    public UserAuthDto getUserAuthByEmail(Email username) {
        UserAuth userAuth = getUserAuthUseCase.execute(username);
        return AuthMapper.toDto(userAuth);
    }

    public UserAuthDto getUserAuthByUserId(UserId userId) {
        UserAuth userAuth = getUserAuthUseCase.execute(userId);
        return AuthMapper.toDto(userAuth);
    }

    public void saveUserCredentials(CredentialsToSaveDto request) {
        saveUserCredentialsUseCase.execute(
                new Email(request.username()),
                new Password(request.password()),
                new UserId(request.userId())
        );
    }

    public void updateUserCredentials(UserId userId, CredentialsUpdateDto fieldsToUpdate) {
        updateUserAuthUseCase.execute(userId, fieldsToUpdate);
    }

    public void updateUserAuth(UserId userId, UserAuthUpdateDto fieldsToUpdate) {
        updateUserAuthUseCase.execute(userId, fieldsToUpdate);
    }

    public void validateOwnerOrAdminAccess(UserId userId) {
        boolean isOwner = Objects.equals(getCurrentLoggedInUserId(), userId);
        boolean isAdmin = isCurrentUserAdmin();
        boolean isNotAdminOrDataOwner = !(isOwner || isAdmin);
        if (isNotAdminOrDataOwner) throw new ForbiddenAccessException();
    }

    public void validateAdminAccess() {
        boolean isAdmin = isCurrentUserAdmin();
        if (!isAdmin) throw new ForbiddenAccessException();
    }

    public UserId getCurrentLoggedInUserId() {
        return getCurrentLoggedInUserUseCase.execute().getUserId();
    }

    public boolean isCurrentUserAdmin() {
        UserAuth userAuth = getCurrentLoggedInUserUseCase.execute();
        return Objects.equals(userAuth.getRole(), Role.ADMIN);
    }
}
