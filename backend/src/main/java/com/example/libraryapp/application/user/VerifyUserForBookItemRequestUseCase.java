package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.Constants;
import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyUserForBookItemRequestUseCase {
    private final UserService userService;

    void execute(Long userId) {
        User user = userService.getUserById(userId);
        if (user.getTotalBooksRequested() >= Constants.MAX_BOOKS_REQUESTED_BY_USER) {
            throw new BookItemRequestException(MessageKey.REQUEST_LIMIT_EXCEEDED);
        }
    }
}
