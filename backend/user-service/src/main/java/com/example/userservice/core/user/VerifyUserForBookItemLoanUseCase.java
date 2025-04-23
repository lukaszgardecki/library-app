package com.example.userservice.core.user;

import com.example.userservice.domain.Constants;
import com.example.userservice.domain.MessageKey;
import com.example.userservice.domain.exception.UnsettledFineException;
import com.example.userservice.domain.exception.UserException;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.UserId;
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
