package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanListPreviewProjection;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.LoanId;
import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBookItemLoanRepositoryAdapter implements BookItemLoanRepositoryPort {
    private final ConcurrentHashMap<LoanId, BookItemLoan> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Optional<BookItemLoan> findById(LoanId id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<BookItemLoan> findByParams(BookItemId bookItemId, UserId userId, LoanStatus status) {
        return map.values().stream()
                .filter(loan -> loan.getBookItemId().equals(bookItemId)
                        && loan.getUserId().equals(userId)
                        && loan.getStatus().equals(status))
                .findFirst();
    }

    @Override
    public Optional<BookItemLoan> findByParams(BookItemId bookItemId, LoanStatus status) {
        return map.values().stream()
                .filter(loan -> loan.getBookItemId().equals(bookItemId)
                        && loan.getStatus().equals(status))
                .findFirst();
    }

    @Override
    public List<BookItemLoan> findAllByUserId(UserId userId) {
        return map.values().stream()
                .filter(loan -> loan.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<BookItemLoan> findAllCurrentLoansByUserId(UserId userId) {
        return map.values().stream()
                .filter(loan -> loan.getUserId().equals(userId) && loan.getStatus() != LoanStatus.CURRENT)
                .toList();
    }

    @Override
    public Page<BookItemLoan> findPageOfBookLoansByParams(UserId userId, LoanStatus status, Pageable pageable) {
        List<BookItemLoan> filteredLoans = map.values().stream()
                .filter(loan -> loan.getUserId().equals(userId) && loan.getStatus().equals(status))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredLoans.size());
        List<BookItemLoan> pagedLoans = filteredLoans.subList(start, end);
        return new PageImpl<>(pagedLoans, pageable, filteredLoans.size());
    }

    @Override
    public Page<BookItemLoanListPreviewProjection> findPageOfBookLoanListPreviews(UserId userId, String query, LoanStatus status, Pageable pageable) {
        // TODO: 20.02.2025 jak to zaimplementować?
        return null;
    }

    @Override
    public List<Object[]> findTopSubjectsWithLoansCount(int limit) {
        // TODO: 12.02.2025 jak to zaimplementować??
        return null;
    }

    @Override
    public BookItemLoan save(BookItemLoan bookItemLoan) {
        if (bookItemLoan.getId() == null) {
            bookItemLoan.setId(new LoanId(++id));
        }
        return map.put(bookItemLoan.getId(), bookItemLoan);
    }

    @Override
    public long countByCreationDate(LocalDate date) {
        return map.values().stream()
                .filter(loan -> loan.getCreationDate().value().toLocalDate().equals(date))
                .count();
    }

    @Override
    public long countUniqueBorrowersInCurrentMonth() {
        YearMonth currentMonth = YearMonth.now();
        return map.values().stream()
                .filter(loan -> {
                    LocalDate loanDate = loan.getCreationDate().value().toLocalDate();
                    return YearMonth.from(loanDate).equals(currentMonth);
                })
                .map(BookItemLoan::getUserId)
                .distinct()
                .count();
    }

    @Override
    public List<Object[]> countBookItemLoansMonthly(LocalDate startDate, LocalDate endDate) {
        return map.values().stream()
                .filter(loan -> {
                    LocalDate loanDate = loan.getCreationDate().value().toLocalDate();
                    return !loanDate.isBefore(startDate) && !loanDate.isAfter(endDate);
                })
                .collect(Collectors.groupingBy(
                        loan -> YearMonth.from(loan.getCreationDate().value().toLocalDate()),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new Object[]{entry.getKey().getMonthValue(), entry.getValue()})
                .sorted(Comparator.comparingInt(o -> (int) o[0]))
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, LoanStatus status) {
        return map.values().stream()
                .filter(loan -> {
                    LocalDate loanDate = loan.getCreationDate().value().toLocalDate();
                    return !loanDate.isBefore(startDate) && !loanDate.isAfter(endDate) && loan.getStatus() == status;
                })
                .collect(Collectors.groupingBy(
                        loan -> loan.getCreationDate().value().getDayOfWeek().getValue(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
                .sorted(Comparator.comparingInt(o -> (int) o[0]))
                .collect(Collectors.toList());
    }
}
