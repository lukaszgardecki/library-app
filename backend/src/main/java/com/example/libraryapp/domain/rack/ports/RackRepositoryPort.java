package com.example.libraryapp.domain.rack.ports;

import com.example.libraryapp.domain.rack.model.Rack;

import java.util.Optional;

public interface RackRepositoryPort {

    Optional<Rack> findById(Long id);

    Optional<Rack> findByLocation(String location);

    Rack save(Rack rack);

    void deleteById(Long id);
}
