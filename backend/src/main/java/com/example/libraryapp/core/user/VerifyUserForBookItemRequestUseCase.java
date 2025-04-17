package com.example.libraryapp.core.user;

import com.example.libraryapp.domain.Constants;
import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyUserForBookItemRequestUseCase {
    private final UserService userService;

    void execute(UserId userId) {
        User user = userService.getUserById(userId);
        if (user.getTotalBooksRequested().value() >= Constants.MAX_BOOKS_REQUESTED_BY_USER) {
            throw new BookItemRequestException(MessageKey.REQUEST_LIMIT_EXCEEDED);
        }
    }
}
