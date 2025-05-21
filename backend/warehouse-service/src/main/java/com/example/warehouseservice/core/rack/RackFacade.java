package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.model.rack.Rack;
import com.example.warehouseservice.domain.model.rack.values.RackId;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class RackFacade {
    private final GetAllRacksUseCase getAllRacksUseCase;
    private final GetRackUseCase getRackUseCase;
    private final AddRackUseCase addRackUseCase;
    private final UpdateRackUseCase updateRackUseCase;
    private final DeleteRackUseCase deleteRackUseCase;

    public Page<Rack> getAllRacksPaged(@Nullable String query, Pageable pageable) {
        return getAllRacksUseCase.execute(query, pageable);
    }

    public List<Rack> getAllRacksList(String query) {
        return getAllRacksUseCase.execute(query);
    }

    public Rack getRackById(RackId id) {
        return getRackUseCase.execute(id);
    }

    public Rack addRack(Rack rack) {
        return addRackUseCase.execute(rack);
    }

    public Rack updateRack(RackId rackId, Rack rack) {
        return updateRackUseCase.execute(rackId, rack);
    }

    public void deleteRack(RackId id) {
        deleteRackUseCase.execute(id);
    }

}
