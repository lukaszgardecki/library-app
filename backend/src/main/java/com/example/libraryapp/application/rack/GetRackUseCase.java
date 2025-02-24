package com.example.libraryapp.application.rack;

import com.example.libraryapp.domain.rack.exceptions.RackNotFoundException;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetRackUseCase {
    private final RackRepositoryPort rackRepository;

    Rack execute(Long id) {
        return rackRepository.findById(id).orElseThrow(() -> new RackNotFoundException(id));
    }

    Rack execute(String location) {
        return rackRepository.findByLocation(location).orElseThrow(() -> new RackNotFoundException(location));
    }
}

