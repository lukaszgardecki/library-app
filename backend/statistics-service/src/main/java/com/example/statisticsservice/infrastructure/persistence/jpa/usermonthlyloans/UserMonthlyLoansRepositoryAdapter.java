package com.example.statisticsservice.infrastructure.persistence.jpa.usermonthlyloans;

import com.example.statisticsservice.domain.integration.LoanCreationDate;
import com.example.statisticsservice.domain.model.borrower.values.UserId;
import com.example.statisticsservice.domain.model.usermonthlyloans.UserMonthlyLoans;
import com.example.statisticsservice.domain.ports.out.UserMonthlyLoansRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;

@Repository
@RequiredArgsConstructor
class UserMonthlyLoansRepositoryAdapter implements UserMonthlyLoansRepositoryPort {
    private final JpaUserMonthlyLoansRepository repository;

    @Override
    public List<UserMonthlyLoans> findAllByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end) {
        return repository.findAllByUserIdAndDateBetween(userId, start, end).stream().map(this::toModel).toList();
    }

    @Override
    @Transactional
    public void incrementUserLoansCount(UserId userId, LoanCreationDate date) {
        tryUpdateOrInsert(userId.value(), date.value().toLocalDate(), repository::incrementUserLoansCount);
    }

    private void tryUpdateOrInsert(Long userId, LocalDate date, BiFunction<Long, LocalDate, Integer> updateFn) {
        int updated = updateFn.apply(userId, date);
        if (updated == 0) {
            try {
                int monthValue = date.getMonthValue();
                int year = date.getYear();
                repository.save(new UserMonthlyLoansEntity(null, userId, year, monthValue, 0));
                updateFn.apply(userId, date);
            } catch (DataIntegrityViolationException e) {
                updateFn.apply(userId, date);
            }
        }
    }

    private UserMonthlyLoansEntity toEntity(UserMonthlyLoans model) {
        return new UserMonthlyLoansEntity(
                model.getId(),
                model.getUserId(),
                model.getYearValue(),
                model.getMonthValue(),
                model.getLoansCount()
        );
    }

    private UserMonthlyLoans toModel(UserMonthlyLoansEntity entity) {
        return new UserMonthlyLoans(
                entity.getId(),
                entity.getUserId(),
                entity.getYearValue(),
                entity.getMonthValue(),
                entity.getLoansCount()
        );
    }
}
