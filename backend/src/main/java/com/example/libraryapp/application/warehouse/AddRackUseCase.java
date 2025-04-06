package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackCreatedDate;
import com.example.libraryapp.domain.rack.model.RackShelvesCount;
import com.example.libraryapp.domain.rack.model.RackUpdatedDate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class AddRackUseCase {
    private final RackService rackService;

    Rack execute(Rack rack) {
        LocalDateTime now = LocalDateTime.now();
        rack.setCreatedDate(new RackCreatedDate(now));
        rack.setUpdatedDate(new RackUpdatedDate(now));
        rack.setShelvesCount(new RackShelvesCount(0));
        return rackService.save(rack);
    }
}
