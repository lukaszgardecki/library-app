package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.Shelf;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import com.example.libraryapp.domain.shelf.ports.ShelfRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryShelfRepositoryAdapter implements ShelfRepositoryPort {
    private final ConcurrentHashMap<ShelfId, Shelf> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Page<Shelf> findAllByParams(RackId rackId, String query, Pageable pageable) {
        List<Shelf> filtered = findAllByParams(rackId, query);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());

        List<Shelf> pageContent = start > filtered.size() ? Collections.emptyList() : filtered.subList(start, end);
        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    @Override
    public List<Shelf> findAllByParams(RackId rackId, String query) {
        return map.values().stream()
                .filter(shelf -> shelf.getRackId().equals(rackId))
                .filter(shelf -> query == null || shelf.getName().value().toLowerCase().contains(query.toLowerCase()))
                .sorted(Comparator.comparingInt(el -> el.getPosition().value()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Shelf> findById(ShelfId id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Integer findMaxPositionByRackId(RackId rackId) {
        return map.values().stream()
                .filter(shelf -> shelf.getRackId().equals(rackId))
                .map(el -> el.getPosition().value())
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    public Shelf save(Shelf shelf) {
        ShelfId rackId = shelf.getId() != null ? shelf.getId() : new ShelfId(++id);
        return map.put(rackId, shelf);
    }

    @Override
    public void deleteById(ShelfId id) {
        map.remove(id);
    }
}
