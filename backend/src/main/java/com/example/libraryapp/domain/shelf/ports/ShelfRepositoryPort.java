package com.example.libraryapp.domain.shelf.ports;

import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.Shelf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShelfRepositoryPort {

    Page<Shelf> findAllByParams(RackId rackId, String query, Pageable pageable);
}
