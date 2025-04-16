package com.example.libraryapp.application.auth;

import com.example.libraryapp.domain.auth.exceptions.ForbiddenAccessException;
import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.model.Email;
import com.example.libraryapp.domain.user.model.Password;
import com.example.libraryapp.domain.user.model.Role;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AuthenticationFacade {
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final GetCurrentLoggedInUserUseCase getCurrentLoggedInUserUseCase;

    public AuthTokensDto authenticate(Email username, Password password) {
        return authenticateUserUseCase.execute(username, password);
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
        return new UserId(getCurrentLoggedInUserUseCase.execute().getId());
    }

    public boolean isCurrentUserAdmin() {
        UserDto user = getCurrentLoggedInUserUseCase.execute();
        return Objects.equals(user.getRole(), Role.ADMIN);
    }
}
