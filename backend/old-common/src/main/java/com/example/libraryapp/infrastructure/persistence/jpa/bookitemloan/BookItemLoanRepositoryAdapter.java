package com.example.libraryapp.infrastructure.persistence.jpa.bookitemloan;

import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.model.*;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
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
    public Optional<BookItemLoan> findById(LoanId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    public Optional<BookItemLoan> findByParams(BookItemId bookItemId, UserId userId, LoanStatus status) {
        return repository.findByParams(bookItemId.value(), userId.value(), status).map(this::toModel);
    }

    @Override
    public Optional<BookItemLoan> findByParams(BookItemId bookItemId, LoanStatus status) {
        return repository.findByParams(bookItemId.value(), status).map(this::toModel);
    }

    @Override
    public List<BookItemLoan> findAllByUserId(UserId userId) {
        return repository.findAllByUserId(userId.value())
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public List<BookItemLoan> findAllCurrentLoansByUserId(UserId userId) {
        LoanStatus statusToFind = LoanStatus.CURRENT;
        return repository.findAllCurrentLoansByUserId(userId.value(), statusToFind)
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Page<BookItemLoan> findPageOfBookLoansByParams(UserId userId, LoanStatus status, Pageable pageable) {
        return repository.findAllByParams(userId.value(), status, pageable).map(this::toModel);
    }

    @Override
    public Page<BookItemLoanListPreviewProjection> findPageOfBookLoanListPreviews(UserId userId, String query, LoanStatus status, Pageable pageable) {
        return repository.findPageOfBookLoanListPreviews(userId.value(), query, status.name(), pageable);
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
    public List<Object[]> countBookItemLoansDaily(LocalDate startDate, LocalDate endDate, LoanStatus status) {
        return repository.countBookItemLoansByDay(startDate.atStartOfDay(), endDate.atStartOfDay(), status);
    }

    private BookItemLoanEntity toEntity(BookItemLoan model) {
        return BookItemLoanEntity.builder()
                .id(model.getId() != null ? model.getId().value() : null)
                .creationDate(model.getCreationDate().value())
                .dueDate(model.getDueDate().value())
                .returnDate(model.getReturnDate().value())
                .status(model.getStatus())
                .userId(model.getUserId().value())
                .bookItemId(model.getBookItemId().value())
                .build();
    }

    private BookItemLoan toModel(BookItemLoanEntity entity) {
        return BookItemLoan.builder()
                .id(new LoanId(entity.getId()))
                .creationDate(new LoanCreationDate(entity.getCreationDate()))
                .dueDate(new LoanDueDate(entity.getDueDate()))
                .returnDate(new LoanReturnDate(entity.getReturnDate()))
                .status(entity.getStatus())
                .userId(new UserId(entity.getUserId()))
                .bookItemId(new BookItemId(entity.getBookItemId()))
                .build();
    }
}
