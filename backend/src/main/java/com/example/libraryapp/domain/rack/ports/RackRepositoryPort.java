package com.example.libraryapp.domain.rack.ports;

import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.rack.model.RackLocationId;

import java.util.Optional;

public interface RackRepositoryPort {

    Optional<Rack> findById(RackId id);

    Optional<Rack> findByLocation(RackLocationId location);

    Rack save(Rack rack);

    void deleteById(RackId id);
}
