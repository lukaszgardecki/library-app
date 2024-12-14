package com.example.libraryapp.NEWapplication.bookitemloan;

import com.example.libraryapp.NEWdomain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoanStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BookItemLoanFacade {
    private final GetBookItemLoanUseCase getBookItemLoanUseCase;
    private final GetPageOfBookItemLoansByParamsUseCase getPageOfBookItemLoansByParamsUseCase;
    private final BorrowBookItemUseCase borrowBookItemUseCase;
    private final RenewBookItemLoanUseCase renewBookItemLoanUseCase;
    private final ReturnBookItemUseCase returnBookItemUseCase;

    public BookItemLoanDto getBookLoan(Long id) {
        BookItemLoan bookItemLoan = getBookItemLoanUseCase.execute(id);
        return BookItemLoanMapper.toDto(bookItemLoan);
    }

    public Page<BookItemLoanDto> getPageOfBookLoansByParams(Long id, BookItemLoanStatus status, Boolean renewable, Pageable pageable) {
        return getPageOfBookItemLoansByParamsUseCase.execute(id, status, renewable, pageable)
                .map(BookItemLoanMapper::toDto);
    }

    public BookItemLoanDto borrowBookItem(Long bookItemId, Long userId) {
        BookItemLoan bookItemLoan = borrowBookItemUseCase.execute(bookItemId, userId);
        return BookItemLoanMapper.toDto(bookItemLoan);
    }

    public BookItemLoanDto renewBookItemLoan(Long bookItemId, Long userId) {
        BookItemLoan bookItemLoan = renewBookItemLoanUseCase.execute(bookItemId, userId);
        return BookItemLoanMapper.toDto(bookItemLoan);
    }

    public void returnBookItem(Long bookItemId, Long userId) {
        returnBookItemUseCase.execute(bookItemId, userId);
    }
}
