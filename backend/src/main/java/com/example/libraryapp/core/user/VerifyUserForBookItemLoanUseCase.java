package com.example.libraryapp.core.user;

import com.example.libraryapp.domain.Constants;
import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.domain.user.exceptions.UnsettledFineException;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class VerifyUserForBookItemLoanUseCase {
    private final UserService userService;

    void execute(UserId userId) {
        User user = userService.getUserById(userId);
        if (user.getCharge().value().compareTo(BigDecimal.ZERO) > 0) throw new UnsettledFineException();
        if (user.getTotalBooksBorrowed().value() >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
            throw new BookItemLoanException(MessageKey.LOAN_LIMIT_EXCEEDED);
        }
    }
}
