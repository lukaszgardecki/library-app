package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetAllRacksUseCase {
    private final RackRepositoryPort rackRepository;

    Page<Rack> execute(String query, Pageable pageable) {
        return rackRepository.findAllByParams(query, pageable);
    }
}
