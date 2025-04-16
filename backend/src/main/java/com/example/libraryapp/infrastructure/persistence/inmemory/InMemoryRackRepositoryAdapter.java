package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryRackRepositoryAdapter implements RackRepositoryPort {
    private final ConcurrentHashMap<RackId, Rack> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Page<Rack> findAllByParams(String query, Pageable pageable) {
        List<Rack> filtered = findAllByParams(query);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());

        List<Rack> pageContent = start > filtered.size() ? Collections.emptyList() : filtered.subList(start, end);
        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    @Override
    public List<Rack> findAllByParams(String query) {
        if (query == null || query.isBlank()) {
            return new ArrayList<>(map.values());
        }

        return map.values().stream()
                .filter(rack -> rack.getName() != null && rack.getName().value().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rack> findById(RackId id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Rack save(Rack rack) {
        RackId rackId = rack.getId() != null ? rack.getId() : new RackId(++id);
        return map.put(rackId, rack);
    }

    @Override
    public void deleteById(RackId id) {
        map.remove(id);
    }
}
