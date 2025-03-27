package com.example.libraryapp.application.rack;

import com.example.libraryapp.domain.rack.exceptions.RackNotFoundException;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.rack.model.RackLocationId;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetRackUseCase {
    private final RackRepositoryPort rackRepository;

    Rack execute(RackId id) {
        return rackRepository.findById(id).orElseThrow(() -> new RackNotFoundException(id));
    }

    Rack execute(RackLocationId location) {
        return rackRepository.findByLocation(location).orElseThrow(() -> new RackNotFoundException(location));
    }
}

