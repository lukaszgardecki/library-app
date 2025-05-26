package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.model.rack.Rack;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
class GetAllRacksUseCase {
    private final RackService rackService;

    Page<Rack> execute(String query, Pageable pageable) {
        return rackService.findAllByParams(query, pageable);
    }

    List<Rack> execute(String query) {
        return rackService.findAllByParams(query);
    }
}
