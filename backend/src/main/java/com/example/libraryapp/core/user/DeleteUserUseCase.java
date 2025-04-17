package com.example.libraryapp.core.user;

import com.example.libraryapp.core.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteUserUseCase {
    private final UserService userService;
    private final BookItemRequestFacade bookItemRequestFacade;

    void execute(UserId userId) {
        userService.validateUserToDelete(userId);
        bookItemRequestFacade.cancelAllItemRequestsByUserId(userId);
        userService.deleteById(userId);
    }
}
