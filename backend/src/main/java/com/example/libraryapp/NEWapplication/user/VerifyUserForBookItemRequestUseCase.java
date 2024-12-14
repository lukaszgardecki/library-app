package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWapplication.constants.Constants;
import com.example.libraryapp.NEWdomain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.NEWdomain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyUserForBookItemRequestUseCase {
    private final UserService userService;

    void execute(Long userId) {
        User user = userService.getUser(userId);
        if (user.getTotalBooksRequested() >= Constants.MAX_BOOKS_REQUESTED_BY_USER) {
            throw new BookItemLoanException("Message.LENDING_LIMIT_EXCEEDED.getMessage()");
        }
    }
}
