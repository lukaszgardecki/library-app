package com.example.libraryapp.NEWapplication.fine;

import com.example.libraryapp.NEWdomain.bookitemloan.dto.BookItemLoanDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FineFacade {
    private final ValidateUserForFinesUseCase validateUserForFinesUseCase;
    private final ProcessBookItemReturnUseCase processBookItemReturnUseCase;


    public void validateUserForFines(Long userId) {
        validateUserForFinesUseCase.execute(userId);
    }

    public void processBookItemReturn(BookItemLoanDto bookItemLoan) {
        processBookItemReturnUseCase.execute(bookItemLoan);
    }
}
