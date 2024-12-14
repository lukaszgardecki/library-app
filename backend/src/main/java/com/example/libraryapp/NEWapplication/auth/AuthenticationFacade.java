package com.example.libraryapp.NEWapplication.auth;

import com.example.libraryapp.NEWdomain.auth.exceptions.ForbiddenAccessException;
import com.example.libraryapp.NEWdomain.token.dto.AuthTokensDto;
import com.example.libraryapp.NEWdomain.user.model.Role;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AuthenticationFacade {
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final GetCurrentLoggedInUserUseCase getCurrentLoggedInUserUseCase;

    public AuthTokensDto authenticate(String username, String password) {
        return authenticateUserUseCase.execute(username, password);
    }

    public boolean isCurrentUserAdmin() {
        UserDto user = getCurrentLoggedInUserUseCase.execute();
        return Objects.equals(user.getRole(), Role.ADMIN);
    }

    public void validateOwnerOrAdminAccess(Long userId) {
        boolean isOwner = Objects.equals(getCurrentLoggedInUserId(), userId);
        boolean isAdmin = isCurrentUserAdmin();
        boolean isNotAdminOrDataOwner = !(isOwner || isAdmin);
        if (isNotAdminOrDataOwner) throw new ForbiddenAccessException();
    }

    public Long getCurrentLoggedInUserId() {
        return getCurrentLoggedInUserUseCase.execute().getId();
    }
}
