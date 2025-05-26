package com.example.warehouseservice.domain.ports.out;

import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.domain.model.shelf.Shelf;
import com.example.warehouseservice.domain.model.shelf.values.ShelfId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ShelfRepositoryPort {

    Page<Shelf> findAllByParams(RackId rackId, String query, Pageable pageable);

    List<Shelf> findAllByParams(RackId rackId, String query);

    Optional<Shelf> findById(ShelfId id);

    Integer findMaxPositionByRackId(RackId rackId);

    Shelf save(Shelf shelf);

    void deleteById(ShelfId id);
}
