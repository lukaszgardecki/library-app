package com.example.libraryapp.application.fine;

import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.bookitemloan.model.LoanId;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class ProcessBookItemReturnUseCase {
    private final FineService fineService;

    void execute(BookItemLoanDto bookItemLoan) {
        fineService.processFineForBookReturn(
                LocalDateTime.now(),
                bookItemLoan.dueDate(),
                new UserId(bookItemLoan.userId()),
                new LoanId(bookItemLoan.id())
        );
    }
}
