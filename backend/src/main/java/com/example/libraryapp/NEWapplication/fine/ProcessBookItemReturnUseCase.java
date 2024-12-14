package com.example.libraryapp.NEWapplication.fine;

import com.example.libraryapp.NEWdomain.bookitemloan.dto.BookItemLoanDto;
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
