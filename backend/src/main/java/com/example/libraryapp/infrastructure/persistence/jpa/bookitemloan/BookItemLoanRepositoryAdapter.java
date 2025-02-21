package com.example.libraryapp.infrastructure.persistence.jpa.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanListPreviewProjection;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookItemLoanRepositoryAdapter implements BookItemLoanRepositoryPort {
    private final JpaBookItemLoanRepository repository;

    @Override
    public Optional<BookItemLoan> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<BookItemLoan> findByParams(Long bookItemId, Long userId, BookItemLoanStatus status) {
        return repository.findByParams(bookItemId, userId, status).map(this::toModel);
    }

    @Override
    public Optional<BookItemLoan> findByParams(Long bookItemId, BookItemLoanStatus status) {
        return repository.findByParams(bookItemId, status).map(this::toModel);
    }

    @Override
    public List<BookItemLoan> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public List<BookItemLoan> findAllCurrentLoansByUserId(Long userId) {
        BookItemLoanStatus statusToFind = BookItemLoanStatus.CURRENT;
        return repository.findAllCurrentLoansByUserId(userId, statusToFind)
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Page<BookItemLoan> findPageOfBookLoansByParams(Long userId, BookItemLoanStatus status, Pageable pageable) {
        return repository.findAllByParams(userId, status, pageable).map(this::toModel);
    }

    @Override
    public Page<BookItemLoanListPreviewProjection> findPageOfBookLoanListPreviews(Long userId, String query, BookItemLoanStatus status, Pageable pageable) {
        return repository.findPageOfBookLoanListPreviews(userId, query, status.name(), pageable);
    }

    @Override
    public List<Object[]> findTopSubjectsWithLoansCount(int limit) {
        return repository.findTopSubjectsWithLoansCount(limit);
    }

    @Override
    @Transactional
    public BookItemLoan save(BookItemLoan bookItemLoan) {
        return toModel(repository.save(toEntity(bookItemLoan)));
    }

    @Override
    public long countByCreationDate(LocalDate date) {
        return repository.countAllByCreationDate(date);
    }

    @Override
    public long countUniqueBorrowersInCurrentMonth() {
        return repository.countUniqueBorrowersInCurrentMonth();
    }

    @Override
    public List<Object[]> countBookItemLoansMonthly(LocalDate startDate, LocalDate endDate) {
        return repository.countBookItemLoansMonthly(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
    }

    @Override
    public List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, BookItemLoanStatus status) {
        return repository.countBookItemLoansByDay(startDate.atStartOfDay(), endDate.atStartOfDay(), status);
    }

    private BookItemLoanEntity toEntity(BookItemLoan model) {
        return BookItemLoanEntity.builder()
                .id(model.getId())
                .creationDate(model.getCreationDate())
                .dueDate(model.getDueDate())
                .returnDate(model.getReturnDate())
                .status(model.getStatus())
                .userId(model.getUserId())
                .bookItemId(model.getBookItemId())
                .build();
    }

    private BookItemLoan toModel(BookItemLoanEntity entity) {
        return BookItemLoan.builder()
                .id(entity.getId())
                .creationDate(entity.getCreationDate())
                .dueDate(entity.getDueDate())
                .returnDate(entity.getReturnDate())
                .status(entity.getStatus())
                .userId(entity.getUserId())
                .bookItemId(entity.getBookItemId())
                .build();
    }
}
