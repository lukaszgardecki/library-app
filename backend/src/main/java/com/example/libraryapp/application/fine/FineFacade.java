package com.example.libraryapp.application.fine;

import com.example.libraryapp.domain.bookitem.model.Price;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.fine.dto.FineCardDetailsDto;
import com.example.libraryapp.domain.fine.model.FineId;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class FineFacade {
    private final ValidateUserForFinesUseCase validateUserForFinesUseCase;
    private final ProcessBookItemReturnUseCase processBookItemReturnUseCase;
    private final ProcessBookItemLostUseCase processBookItemLostUseCase;
    private final PayFineUseCase payFineUseCase;
    private final CancelFineUseCase cancelFineUseCase;

    public void validateUserForFines(UserId userId) {
        validateUserForFinesUseCase.execute(userId);
    }

    public void processBookItemReturn(BookItemLoanDto bookItemLoan) {
        processBookItemReturnUseCase.execute(bookItemLoan);
    }

    public void processBookItemLost(BookItemLoanDto bookItemLoan, Price bookItemPrice) {
        processBookItemLostUseCase.execute(bookItemLoan, bookItemPrice);
    }

    public void payFine(FineId fineId, FineCardDetailsDto cardDetails) {
        payFineUseCase.execute(fineId, cardDetails);
    }

    public void cancelFine(FineId fineId) {
        cancelFineUseCase.execute(fineId);
    }
}
