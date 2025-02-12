package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.fine.model.Fine;
import com.example.libraryapp.domain.fine.model.FineStatus;
import com.example.libraryapp.domain.fine.ports.FineRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryFineRepositoryAdapter implements FineRepository {
    private final ConcurrentHashMap<Long, Fine> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public List<Fine> findAllByUserId(Long userId) {
        return map.values().stream()
                .filter(fine -> fine.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Optional<Fine> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Fine save(Fine fineToSave) {
        requireNonNull(fineToSave, "Fine to save cannot be null");
        if (fineToSave.getId() == null) {
            fineToSave.setId(++id);
        }
        return map.put(fineToSave.getId(), fineToSave);
    }

    @Override
    public void setStatus(Long id, FineStatus status) {
        Optional<Fine> fine = findById(id);
        fine.ifPresent(f -> f.setStatus(status));
    }
}
