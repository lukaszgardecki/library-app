package com.example.libraryapp.core.user;

import com.example.libraryapp.core.auth.AuthenticationFacade;
import com.example.libraryapp.domain.user.model.UserDetailsAdmin;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserDetailsAdminUseCase {
    private final UserService userService;
    private final AuthenticationFacade authFacade;

    UserDetailsAdmin execute(UserId id) {
        authFacade.validateAdminAccess();
        return userService.getAdminUserDetailsById(id);
    }
}
