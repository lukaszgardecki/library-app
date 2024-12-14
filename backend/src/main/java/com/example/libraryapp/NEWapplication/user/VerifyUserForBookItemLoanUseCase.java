package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWapplication.constants.Constants;
import com.example.libraryapp.NEWdomain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.NEWdomain.user.exceptions.UnsettledFineException;
import com.example.libraryapp.NEWdomain.user.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class VerifyUserForBookItemLoanUseCase {
    private final UserService userService;

    void execute(Long userId) {
        User user = userService.getUser(userId);
        if (user.getCharge().compareTo(BigDecimal.ZERO) > 0) throw new UnsettledFineException();
        if (user.getTotalBooksBorrowed() >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
            throw new BookItemLoanException("Message.LENDING_LIMIT_EXCEEDED.getMessage()");
        }
    }
}
