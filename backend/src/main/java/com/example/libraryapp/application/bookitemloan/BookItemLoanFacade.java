package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanListPreviewDto;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanId;
import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class BookItemLoanFacade {
    private final GetBookItemLoanUseCase getBookItemLoanUseCase;
    private final GetPageOfBookItemLoansByParamsUseCase getPageOfBookItemLoansByParamsUseCase;
    private final GetPageOfBookItemLoanListPreviewsUseCase getPageOfBookItemLoanListPreviewsUseCase;
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

    public BookItemLoanDto getBookLoan(LoanId id) {
        BookItemLoan bookItemLoan = getBookItemLoanUseCase.execute(id);
        return BookItemLoanMapper.toDto(bookItemLoan);
    }

    public Page<BookItemLoanDto> getPageOfBookLoansByParams(UserId userId, LoanStatus status, Boolean renewable, Pageable pageable) {
        return getPageOfBookItemLoansByParamsUseCase.execute(userId, status, renewable, pageable)
                .map(BookItemLoanMapper::toDto);
    }

    public Page<BookItemLoanListPreviewDto> getPageOfBookLoanListPreviewsByParams(UserId userId, String query, LoanStatus status, Boolean renewable, Pageable pageable) {
        return getPageOfBookItemLoanListPreviewsUseCase.execute(userId, query, status, renewable, pageable)
                .map(BookItemLoanMapper::toDto);
    }

    public List<BookItemLoanDto> getAllLoansByUserId(UserId userId) {
        return getAllUserLoansUseCase.execute(userId)
                .stream()
                .map(BookItemLoanMapper::toDto)
                .toList();
    }

    public List<BookItemLoanDto> getCurrentLoansByUserId(UserId userId) {
        return getUserCurrentLoansUseCase.execute(userId)
                .stream()
                .map(BookItemLoanMapper::toDto)
                .toList();
    }

    public Map<String, Long> getTopSubjectsWithLoansCountUseCase(int limit) {
        return getTopSubjectsWithLoansCountUseCase.execute(limit);
    }

    public BookItemLoanDto borrowBookItem(BookItemId bookItemId, UserId userId) {
        BookItemLoan bookItemLoan = borrowBookItemUseCase.execute(bookItemId, userId);
        return BookItemLoanMapper.toDto(bookItemLoan);
    }

    public BookItemLoanDto renewBookItemLoan(BookItemId bookItemId, UserId userId) {
        BookItemLoan bookItemLoan = renewBookItemLoanUseCase.execute(bookItemId, userId);
        return BookItemLoanMapper.toDto(bookItemLoan);
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
