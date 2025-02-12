package com.example.libraryapp.application.user;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteUserUseCase {
    private final UserService userService;
    private final AuthenticationFacade authFacade;
    private final BookItemRequestFacade bookItemRequestFacade;

    void execute(Long userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        userService.validateUserToDelete(userId);
        bookItemRequestFacade.cancelAllItemRequestsByUserId(userId);
        userService.deleteById(userId);
    }
}
