package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
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

    public BookItemLoanDto getBookLoan(Long id) {
        BookItemLoan bookItemLoan = getBookItemLoanUseCase.execute(id);
        return BookItemLoanMapper.toDto(bookItemLoan);
    }

    public Page<BookItemLoanDto> getPageOfBookLoansByParams(Long id, BookItemLoanStatus status, Boolean renewable, Pageable pageable) {
        return getPageOfBookItemLoansByParamsUseCase.execute(id, status, renewable, pageable)
                .map(BookItemLoanMapper::toDto);
    }

    public List<BookItemLoanDto> getAllLoansByUserId(Long userId) {
        return getAllUserLoansUseCase.execute(userId)
                .stream()
                .map(BookItemLoanMapper::toDto)
                .toList();
    }

    public List<BookItemLoanDto> getCurrentLoansByUserId(Long userId) {
        return getUserCurrentLoansUseCase.execute(userId)
                .stream()
                .map(BookItemLoanMapper::toDto)
                .toList();
    }

    public Map<String, Long> getTopSubjectsWithLoansCountUseCase(int limit) {
        return getTopSubjectsWithLoansCountUseCase.execute(limit);
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

    public void processLostBookItem(Long bookItemId, Long userId) {
        processLostBookItemUseCase.execute(bookItemId, userId);
    }

    public long countByCreationDate(LocalDateTime day) {
        return countByCreationDateUseCase.execute(day);
    }

    public long countUniqueBorrowersInCurrentMonth() {
        return countUniqueBorrowersInCurrentMonthUseCase.execute();
    }

    public List<Object[]> countBookItemLoansMonthly(LocalDate startDate, LocalDate endDate) {
        return countBookItemLoansMonthlyUseCase.execute(startDate, endDate);
    }

    public List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, BookItemLoanStatus status) {
        return countBookItemLoansDailyUseCase.execute(startDate, endDate, status);
    }
}
