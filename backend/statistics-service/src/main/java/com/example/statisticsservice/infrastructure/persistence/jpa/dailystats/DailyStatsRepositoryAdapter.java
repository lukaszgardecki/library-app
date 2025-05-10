package com.example.statisticsservice.infrastructure.persistence.jpa.dailystats;

import com.example.statisticsservice.domain.integration.LoanCreationDate;
import com.example.statisticsservice.domain.integration.LoanReturnDate;
import com.example.statisticsservice.domain.model.dailystats.DailyStats;
import com.example.statisticsservice.domain.ports.out.DailyStatsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
class DailyStatsRepositoryAdapter implements DailyStatsRepositoryPort {
    private final JpaDailyStatsRepository repository;

    @Override
    public Optional<DailyStats> findByDate(LocalDate date) {
        return repository.findByDate(date).map(this::toModel);
    }

    @Override
    public List<DailyStats> findAllByMonthValue(int month) {
        return repository.findAllByMonthValue(month).stream().map(this::toModel).toList();
    }

    @Override
    public List<DailyStats> findAllByDateBetween(LocalDate start, LocalDate end) {
        return repository.findAllByDateBetween(start, end).stream().map(this::toModel).toList();
    }

    @Override
    @Transactional
    public void incrementNewUsersCount(LocalDate date) {
        tryUpdateOrInsert(date, repository::incrementNewUsersCount);
    }

    @Override
    @Transactional
    public void decrementNewUsersCount(LocalDate date) {
        tryUpdateOrInsert(date, repository::decrementNewUsersCount);
    }

    @Override
    @Transactional
    public void incrementNewLoansCount(LoanCreationDate date) {
        tryUpdateOrInsert(date.value().toLocalDate(), repository::incrementNewLoansCount);
    }

    @Override
    @Transactional
    public void incrementReturnedLoansCount(LoanReturnDate date) {
        tryUpdateOrInsert(date.value().toLocalDate(), repository::incrementReturnedLoansCount);
    }

    private void tryUpdateOrInsert(LocalDate date, Function<LocalDate, Integer> updateFn) {
        int updated = updateFn.apply(date);
        if (updated == 0) {
            try {
                repository.save(new DailyStatsEntity(date, 0, 0, 0));
                updateFn.apply(date);
            } catch (DataIntegrityViolationException e) {
                updateFn.apply(date);
            }
        }
    }

    private DailyStatsEntity toEntity(DailyStats model) {
        return new DailyStatsEntity(
                model.getDate(),
                model.getNewLoans(),
                model.getReturnedLoans(),
                model.getNewUsers()
        );
    }

    private DailyStats toModel(DailyStatsEntity entity) {
        return new DailyStats(
                entity.getDate(),
                entity.getNewLoans(),
                entity.getReturnedLoans(),
                entity.getNewUsers()
        );
    }
}
