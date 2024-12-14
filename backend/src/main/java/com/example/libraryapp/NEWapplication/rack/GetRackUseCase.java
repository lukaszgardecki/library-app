package com.example.libraryapp.NEWapplication.rack;

import com.example.libraryapp.NEWdomain.rack.exceptions.RackNotFoundException;
import com.example.libraryapp.NEWdomain.rack.model.Rack;
import com.example.libraryapp.NEWdomain.rack.ports.RackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetRackUseCase {
    private final RackRepository rackRepository;

    Rack execute(Long id) {
        return rackRepository.findById(id).orElseThrow(() -> new RackNotFoundException(id));
    }

    Rack execute(String location) {
        return rackRepository.findByLocation(location).orElseThrow(() -> new RackNotFoundException(location));
    }
}

