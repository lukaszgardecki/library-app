package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class BookItemLoanFacade {
    private final GetBookItemLoanUseCase getBookItemLoanUseCase;
    private final GetPageOfBookItemLoansByParamsUseCase getPageOfBookItemLoansByParamsUseCase;
    private final GetAllUserLoansUseCase getAllUserLoansUseCase;
    private final GetUserCurrentLoansUseCase getUserCurrentLoansUseCase;
    private final GetTopSubjectsWithLoansCountUseCase getTopSubjectsWithLoansCountUseCase;
    private final BorrowBookItemUseCase borrowBookItemUseCase;
    private final RenewBookItemLoanUseCase renewBookItemLoanUseCase;
    private final ReturnBookItemUseCase returnBookItemUseCase;
    private final ProcessLostBookItemUseCase processLostBookItemUseCase;
    private final CountByCreationDateUseCase countByCreationDateUseCase;
    private final CountUniqueBorrowersInCurrentMonthUseCase countUniqueBorrowersInCurrentMonthUseCase;
    private final CountBookItemLoansMonthlyUseCase countBookItemLoansMonthlyUseCase;
    private final CountBookItemLoansDailyUseCase countBookItemLoansDailyUseCase;

    public BookItemLoan getBookLoan(LoanId id) {
        return getBookItemLoanUseCase.execute(id);
    }

    public Page<BookItemLoan> getPageOfBookLoansByParams(UserId userId, LoanStatus status, Boolean renewable, Pageable pageable) {
        return getPageOfBookItemLoansByParamsUseCase.execute(userId, status, renewable, pageable);
    }

    public List<BookItemLoan> getAllLoansByUserId(UserId userId) {
        return getAllUserLoansUseCase.execute(userId);
    }

    public Page<BookItemLoan> getCurrentLoansByUserId(UserId userId, Pageable pageable) {
        return getUserCurrentLoansUseCase.execute(userId, pageable);
    }

    public List<BookItemLoan> getCurrentLoansByUserId(UserId userId) {
        return getUserCurrentLoansUseCase.execute(userId);
    }

    public Map<String, Long> getTopSubjectsWithLoansCountUseCase(int limit) {
        return getTopSubjectsWithLoansCountUseCase.execute(limit);
    }

    public BookItemLoan borrowBookItem(BookItemId bookItemId, UserId userId) {
        return borrowBookItemUseCase.execute(bookItemId, userId);
    }

    public BookItemLoan renewBookItemLoan(BookItemId bookItemId, UserId userId) {
        return renewBookItemLoanUseCase.execute(bookItemId, userId);
    }

    public void returnBookItem(BookItemId bookItemId, UserId userId) {
        returnBookItemUseCase.execute(bookItemId, userId);
    }

    public void processLostBookItem(BookItemId bookItemId, UserId userId) {
        processLostBookItemUseCase.execute(bookItemId, userId);
    }

    public long countByCreationDate(LoanCreationDate day) {
        return countByCreationDateUseCase.execute(day);
    }

    public long countUniqueBorrowersInCurrentMonth() {
        return countUniqueBorrowersInCurrentMonthUseCase.execute();
    }

    public List<Object[]> countBookItemLoansMonthly(LocalDate startDate, LocalDate endDate) {
        return countBookItemLoansMonthlyUseCase.execute(startDate, endDate);
    }

    public List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, LoanStatus status) {
        return countBookItemLoansDailyUseCase.execute(startDate, endDate, status);
    }
}
