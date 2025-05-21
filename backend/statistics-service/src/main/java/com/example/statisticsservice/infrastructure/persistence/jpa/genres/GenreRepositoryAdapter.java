package com.example.statisticsservice.infrastructure.persistence.jpa.genres;

import com.example.statisticsservice.domain.integration.Subject;
import com.example.statisticsservice.domain.model.genre.Genre;
import com.example.statisticsservice.domain.ports.out.GenreRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
class GenreRepositoryAdapter implements GenreRepositoryPort {
    private final JpaGenreRepository repository;

    @Override
    public List<Genre> findAllOrderByLoansDesc(int limit) {
        return repository.findAllOrderByLoansDesc(limit).stream().map(this::toModel).toList();
    }

    @Override
    @Transactional
    public void incrementLoansCount(Subject genre) {
        tryUpdateOrInsert(genre.value(), repository::incrementLoansCount);
    }

    private void tryUpdateOrInsert(String city, Function<String, Integer> updateFn) {
        int updated = updateFn.apply(city);
        if (updated == 0) {
            try {
                repository.save(new GenreEntity(city, 0));
                updateFn.apply(city);
            } catch (DataIntegrityViolationException e) {
                updateFn.apply(city);
            }
        }
    }

    private GenreEntity toEntity(Genre model) {
        return new GenreEntity(
                model.getName(),
                model.getLoans()
        );
    }

    private Genre toModel(GenreEntity entity) {
        return new Genre(
                entity.getName(),
                entity.getLoans()
        );
    }
}
