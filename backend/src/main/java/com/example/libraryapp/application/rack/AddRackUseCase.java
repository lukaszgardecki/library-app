package com.example.libraryapp.application.rack;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.rack.exceptions.RackException;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddRackUseCase {
    private final RackRepositoryPort rackRepository;

    Rack execute(Rack rack) {
        rackRepository.findByLocation(rack.getLocationIdentifier())
                .ifPresent(r -> {
                    throw new RackException(MessageKey.RACK_LOCATION_ALREADY_EXISTS);
                });
        return rackRepository.save(rack);
    }
}
