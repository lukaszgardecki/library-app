package com.example.fineservice.core;

import com.example.fineservice.domain.dto.PaymentCardDetailsDto;
import com.example.fineservice.domain.model.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FineFacade {
    private final VerifyUserForFinesUseCase verifyUserForFinesUseCase;
    private final ProcessBookItemReturnUseCase processBookItemReturnUseCase;
    private final ProcessBookItemLostUseCase processBookItemLostUseCase;
    private final PayFineUseCase payFineUseCase;
    private final CancelFineUseCase cancelFineUseCase;

    public void verifyUserForFines(UserId userId) {
        verifyUserForFinesUseCase.execute(userId);
    }

    public void processBookItemReturn(LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate) {
        processBookItemReturnUseCase.execute(loanId, userId, returnDate, dueDate);
    }

    public void processBookItemLost(
            LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate, Price bookItemPrice
    ) {
        processBookItemLostUseCase.execute(loanId, userId, returnDate, dueDate, bookItemPrice);
    }

    public void payFine(FineId fineId, PaymentCardDetailsDto cardDetails) {
        payFineUseCase.execute(fineId, cardDetails);
    }

    public void cancelFine(FineId fineId) {
        cancelFineUseCase.execute(fineId);
    }
}
