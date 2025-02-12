package com.example.libraryapp.application.fine;

import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.fine.dto.FineCardDetailsDto;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class FineFacade {
    private final ValidateUserForFinesUseCase validateUserForFinesUseCase;
    private final ProcessBookItemReturnUseCase processBookItemReturnUseCase;
    private final ProcessBookItemLostUseCase processBookItemLostUseCase;
    private final PayFineUseCase payFineUseCase;
    private final CancelFineUseCase cancelFineUseCase;

    public void validateUserForFines(Long userId) {
        validateUserForFinesUseCase.execute(userId);
    }

    public void processBookItemReturn(BookItemLoanDto bookItemLoan) {
        processBookItemReturnUseCase.execute(bookItemLoan);
    }

    public void processBookItemLost(BookItemLoanDto bookItemLoan, BigDecimal bookItemPrice) {
        processBookItemLostUseCase.execute(bookItemLoan, bookItemPrice);
    }

    public void payFine(Long fineId, FineCardDetailsDto cardDetails) {
        payFineUseCase.execute(fineId, cardDetails);
    }

    public void cancelFine(Long fineId) {
        cancelFineUseCase.execute(fineId);
    }
}
