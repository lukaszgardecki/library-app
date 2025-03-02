package com.example.libraryapp.application.user;

import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteUserUseCase {
    private final UserService userService;
    private final BookItemRequestFacade bookItemRequestFacade;

    void execute(Long userId) {
        userService.validateUserToDelete(userId);
        bookItemRequestFacade.cancelAllItemRequestsByUserId(userId);
        userService.deleteById(userId);
    }
}
