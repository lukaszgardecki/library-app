package com.example.libraryapp.domain.rack.ports;

import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RackRepositoryPort {

    Page<Rack> findAllByParams(String query, Pageable pageable);

    Optional<Rack> findById(RackId id);

    Rack save(Rack rack);

    void deleteById(RackId id);
}
