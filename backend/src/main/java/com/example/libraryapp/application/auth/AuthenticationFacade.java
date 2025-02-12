package com.example.libraryapp.application.auth;

import com.example.libraryapp.domain.auth.exceptions.ForbiddenAccessException;
import com.example.libraryapp.domain.token.dto.AuthTokensDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.model.Role;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AuthenticationFacade {
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final GetCurrentLoggedInUserUseCase getCurrentLoggedInUserUseCase;

    public AuthTokensDto authenticate(String username, String password) {
        return authenticateUserUseCase.execute(username, password);
    }

    public void validateOwnerOrAdminAccess(Long userId) {
        boolean isOwner = Objects.equals(getCurrentLoggedInUserId(), userId);
        boolean isAdmin = isCurrentUserAdmin();
        boolean isNotAdminOrDataOwner = !(isOwner || isAdmin);
        if (isNotAdminOrDataOwner) throw new ForbiddenAccessException();
    }

    public void validateAdminAccess() {
        boolean isAdmin = isCurrentUserAdmin();
        if (!isAdmin) throw new ForbiddenAccessException();
    }

    public Long getCurrentLoggedInUserId() {
        return getCurrentLoggedInUserUseCase.execute().getId();
    }

    private boolean isCurrentUserAdmin() {
        UserDto user = getCurrentLoggedInUserUseCase.execute();
        return Objects.equals(user.getRole(), Role.ADMIN);
    }
}
