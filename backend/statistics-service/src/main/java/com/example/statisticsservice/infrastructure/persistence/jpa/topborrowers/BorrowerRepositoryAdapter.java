package com.example.statisticsservice.infrastructure.persistence.jpa.topborrowers;

import com.example.statisticsservice.domain.integration.BirthDate;
import com.example.statisticsservice.domain.integration.LoanCreationDate;
import com.example.statisticsservice.domain.model.borrower.Borrower;
import com.example.statisticsservice.domain.model.borrower.values.*;
import com.example.statisticsservice.domain.ports.out.BorrowerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
class BorrowerRepositoryAdapter implements BorrowerRepositoryPort {
    private final JpaBorrowerRepository repository;

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public int countUsersByAgeBetween(int fromAge, int toAge) {
        LocalDate today = LocalDate.now();
        LocalDate youngest = today.minusYears(fromAge);
        LocalDate oldest = today.minusYears(toAge);
        return repository.countUsersByAgeBetween(oldest, youngest);
    }

    @Override
    public List<Borrower> getBorrowers(int limit) {
        return repository.findAll(limit).stream().map(this::toModel).toList();
    }

    @Override
    public List<Borrower> findAllByLastLoanMonth(int month) {
        return repository.findAllByLastLoanMonth(month).stream().map(this::toModel).toList();
    }

    @Override
    @Transactional
    public void saveNewBorrower(Borrower borrower) {
        repository.save(toEntity(borrower));
    }

    @Override
    @Transactional
    public void incrementLoansCount(UserId userId) {
        repository.incrementLoansCount(userId.value());
    }

    @Override
    @Transactional
    public void changeLastLoanDate(UserId userId, LoanCreationDate newDate) {
        repository.changeLastLoanDate(userId.value(), newDate.value().toLocalDate());
    }

    private BorrowerEntity toEntity(Borrower model) {
        return new BorrowerEntity(
                model.getId() != null ? model.getId().value() : null,
                model.getUserId().value(),
                model.getFirstName().value(),
                model.getLastName().value(),
                model.getBirthday().value(),
                model.getLoans().value(),
                model.getLastLoanDate() != null ? model.getLastLoanDate() : null
        );
    }

    private Borrower toModel(BorrowerEntity entity) {
        return new Borrower(
                new BorrowerId(entity.getId()),
                new UserId(entity.getUserId()),
                new PersonFirstName(entity.getFirstName()),
                new PersonLastName(entity.getLastName()),
                new BirthDate(entity.getBirthday()),
                new LoansCount(entity.getLoansCount()),
                entity.getLastLoanDate()
        );
    }
}
