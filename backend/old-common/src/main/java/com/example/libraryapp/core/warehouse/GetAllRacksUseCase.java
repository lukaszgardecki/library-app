package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
class GetAllRacksUseCase {
    private final RackRepositoryPort rackRepository;

    Page<Rack> execute(String query, Pageable pageable) {
        return rackRepository.findAllByParams(query, pageable);
    }

    List<Rack> execute(String query) {
        return rackRepository.findAllByParams(query);
    }
}
