package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.Shelf;
import com.example.libraryapp.domain.shelf.ports.ShelfRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
class GetAllShelvesUseCase {
    private final ShelfRepositoryPort shelfRepository;

    Page<Shelf> execute(RackId rackId, String query, Pageable pageable) {
        return shelfRepository.findAllByParams(rackId, query, pageable);
    }

    List<Shelf> execute(RackId rackId, String query) {
        return shelfRepository.findAllByParams(rackId, query);
    }
}
