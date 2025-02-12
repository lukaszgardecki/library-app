package com.example.libraryapp.application.user;

import com.example.libraryapp.application.constants.Constants;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyUserForBookItemRequestUseCase {
    private final UserService userService;

    void execute(Long userId) {
        User user = userService.getUserById(userId);
        if (user.getTotalBooksRequested() >= Constants.MAX_BOOKS_REQUESTED_BY_USER) {
            throw new BookItemLoanException("Message.LENDING_LIMIT_EXCEEDED.getMessage()");
        }
    }
}
