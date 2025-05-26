package com.example.statisticsservice.infrastructure.persistence.jpa.cities;

import com.example.statisticsservice.domain.integration.City;
import com.example.statisticsservice.domain.model.city.CityStats;
import com.example.statisticsservice.domain.ports.out.CityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
class CityRepositoryAdapter implements CityRepositoryPort {
    private final JpaCityRepository repository;

    @Override
    public List<CityStats> findAllOrderByUsersDesc(int limit) {
        return repository.findAllOrderByUsersDesc(limit).stream().map(this::toModel).toList();
    }

    @Override
    @Transactional
    public void incrementUsersCount(City city) {
        tryUpdateOrInsert(city, repository::incrementUsersCount);
    }

    @Override
    @Transactional
    public void decrementUsersCount(City city) {
        tryUpdateOrInsert(city, repository::decrementUsersCount);
    }

    private void tryUpdateOrInsert(City city, Function<String, Integer> updateFn) {
        int updated = updateFn.apply(city.value());
        if (updated == 0) {
            try {
                repository.save(new CityEntity(city.value(), 0));
                updateFn.apply(city.value());
            } catch (DataIntegrityViolationException e) {
                updateFn.apply(city.value());
            }
        }
    }

    private CityEntity toEntity(CityStats model) {
        return new CityEntity(
                model.getName(),
                model.getUsers()
        );
    }

    private CityStats toModel(CityEntity entity) {
        return new CityStats(
                entity.getName(),
                entity.getUsers()
        );
    }
}
