package com.example.libraryapp.application.fine;

import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class ProcessBookItemReturnUseCase {
    private final FineService fineService;

    void execute(BookItemLoanDto bookItemLoan) {
        fineService.processFineForBookReturn(
                LocalDateTime.now(), bookItemLoan.dueDate(), bookItemLoan.userId(), bookItemLoan.id()
        );
    }
}
