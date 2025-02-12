package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepository;
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

public class InMemoryBookItemLoanRepositoryImpl implements BookItemLoanRepository {
    private final ConcurrentHashMap<Long, BookItemLoan> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Optional<BookItemLoan> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<BookItemLoan> findByParams(Long bookItemId, Long userId, BookItemLoanStatus status) {
        return map.values().stream()
                .filter(loan -> loan.getBookItemId().equals(bookItemId)
                        && loan.getUserId().equals(userId)
                        && loan.getStatus().equals(status))
                .findFirst();
    }

    @Override
    public List<BookItemLoan> findAllByUserId(Long userId) {
        return map.values().stream()
                .filter(loan -> loan.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<BookItemLoan> findAllCurrentLoansByUserId(Long userId) {
        return map.values().stream()
                .filter(loan -> loan.getUserId().equals(userId) && loan.getStatus() != BookItemLoanStatus.CURRENT)
                .toList();
    }

    @Override
    public Page<BookItemLoan> findPageOfBookLoansByParams(Long id, BookItemLoanStatus status, Pageable pageable) {
        List<BookItemLoan> filteredLoans = map.values().stream()
                .filter(loan -> loan.getBookItemId().equals(id) && loan.getStatus().equals(status))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredLoans.size());
        List<BookItemLoan> pagedLoans = filteredLoans.subList(start, end);
        return new PageImpl<>(pagedLoans, pageable, filteredLoans.size());
    }

    @Override
    public List<Object[]> findTopSubjectsWithLoansCount(int limit) {
        // TODO: 12.02.2025 jak to zaimplementować??
        return null;
    }

    @Override
    public BookItemLoan save(BookItemLoan bookItemLoan) {
        if (bookItemLoan.getId() == null) {
            bookItemLoan.setId(++id);
        }
        return map.put(bookItemLoan.getId(), bookItemLoan);
    }

    @Override
    public long countByCreationDate(LocalDate date) {
        return map.values().stream()
                .filter(loan -> loan.getCreationDate().toLocalDate().equals(date))
                .count();
    }

    @Override
    public long countUniqueBorrowersInCurrentMonth() {
        YearMonth currentMonth = YearMonth.now();
        return map.values().stream()
                .filter(loan -> {
                    LocalDate loanDate = loan.getCreationDate().toLocalDate();
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
                    LocalDate loanDate = loan.getCreationDate().toLocalDate();
                    return !loanDate.isBefore(startDate) && !loanDate.isAfter(endDate);
                })
                .collect(Collectors.groupingBy(
                        loan -> YearMonth.from(loan.getCreationDate().toLocalDate()),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new Object[]{entry.getKey().getMonthValue(), entry.getValue()})
                .sorted(Comparator.comparingInt(o -> (int) o[0]))
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, BookItemLoanStatus status) {
        return map.values().stream()
                .filter(loan -> {
                    LocalDate loanDate = loan.getCreationDate().toLocalDate();
                    return !loanDate.isBefore(startDate) && !loanDate.isAfter(endDate) && loan.getStatus() == status;
                })
                .collect(Collectors.groupingBy(
                        loan -> loan.getCreationDate().getDayOfWeek().getValue(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
                .sorted(Comparator.comparingInt(o -> (int) o[0]))
                .collect(Collectors.toList());
    }
}
