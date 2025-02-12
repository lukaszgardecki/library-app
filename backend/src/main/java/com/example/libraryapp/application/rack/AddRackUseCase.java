package com.example.libraryapp.application.rack;

import com.example.libraryapp.domain.rack.exceptions.RackException;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.ports.RackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddRackUseCase {
    private final RackRepository rackRepository;

    Rack execute(Rack rack) {
        rackRepository.findByLocation(rack.getLocationIdentifier())
                .ifPresent(r -> {
                    throw new RackException("Message.RACK_ALREADY_EXISTS.getMessage(r.getLocationIdentifier())");
                });
        return rackRepository.save(rack);
    }
}
