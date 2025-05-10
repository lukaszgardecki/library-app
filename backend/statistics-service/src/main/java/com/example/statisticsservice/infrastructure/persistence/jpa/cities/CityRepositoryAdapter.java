package com.example.statisticsservice.infrastructure.persistence.jpa.cities;

import com.example.statisticsservice.domain.model.city.City;
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
    public List<City> findAllOrderByUsersDesc(int limit) {
        return repository.findAllOrderByUsersDesc(limit).stream().map(this::toModel).toList();
    }

    @Override
    @Transactional
    public void incrementUsersCount(String city) {
        tryUpdateOrInsert(city, repository::incrementUsersCount);
    }

    @Override
    @Transactional
    public void decrementUsersCount(String city) {
        tryUpdateOrInsert(city, repository::decrementUsersCount);
    }

    private void tryUpdateOrInsert(String city, Function<String, Integer> updateFn) {
        int updated = updateFn.apply(city);
        if (updated == 0) {
            try {
                repository.save(new CityEntity(city, 0));
                updateFn.apply(city);
            } catch (DataIntegrityViolationException e) {
                updateFn.apply(city);
            }
        }
    }

    private CityEntity toEntity(City model) {
        return new CityEntity(
                model.getName(),
                model.getUsers()
        );
    }

    private City toModel(CityEntity entity) {
        return new City(
                entity.getName(),
                entity.getUsers()
        );
    }
}
