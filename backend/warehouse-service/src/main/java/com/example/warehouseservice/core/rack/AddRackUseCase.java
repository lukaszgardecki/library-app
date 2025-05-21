package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.model.rack.Rack;
import com.example.warehouseservice.domain.model.rack.values.RackCreatedDate;
import com.example.warehouseservice.domain.model.rack.values.RackShelvesCount;
import com.example.warehouseservice.domain.model.rack.values.RackUpdatedDate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class AddRackUseCase {
    private final RackService rackService;

    Rack execute(Rack rack) {
        rack.setCreatedDate(new RackCreatedDate(LocalDateTime.now()));
        rack.setUpdatedDate(new RackUpdatedDate(null));
        rack.setShelvesCount(new RackShelvesCount(0));
        return rackService.save(rack);
    }
}
