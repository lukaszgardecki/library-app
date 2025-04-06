package com.example.libraryapp.domain.shelf.ports;

import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.Shelf;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ShelfRepositoryPort {

    Page<Shelf> findAllByParams(RackId rackId, String query, Pageable pageable);

    Optional<Shelf> findById(ShelfId id);

    Integer findMaxPositionByRackId(RackId rackId);

    Shelf save(Shelf shelf);

    void deleteById(ShelfId id);
}
