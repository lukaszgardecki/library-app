package com.example.userservice.user.core;

import com.example.userservice.common.Constants;
import com.example.userservice.common.MessageKey;
import com.example.userservice.user.domain.exceptions.UnsettledFineException;
import com.example.userservice.user.domain.exceptions.UserException;
import com.example.userservice.user.domain.model.user.User;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class VerifyUserForBookItemLoanUseCase {
    private final UserService userService;

    void execute(UserId userId) {
        User user = userService.getUserById(userId);
        if (user.getCharge().value().compareTo(BigDecimal.ZERO) > 0) throw new UnsettledFineException();
        if (user.getTotalBooksBorrowed().value() >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
            throw new UserException(MessageKey.LOAN_LIMIT_EXCEEDED);
        }
    }
}
