package com.example.warehouseservice.domain.ports;

import com.example.warehouseservice.domain.model.rack.Rack;
import com.example.warehouseservice.domain.model.rack.RackId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RackRepositoryPort {

    Page<Rack> findAllByParams(String query, Pageable pageable);

    List<Rack> findAllByParams(String query);

    Optional<Rack> findById(RackId id);

    Rack save(Rack rack);

    void deleteById(RackId id);
}
