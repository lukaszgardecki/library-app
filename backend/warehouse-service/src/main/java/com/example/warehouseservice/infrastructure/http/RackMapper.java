package com.example.warehouseservice.infrastructure.http;

import com.example.warehouseservice.domain.model.rack.Rack;
import com.example.warehouseservice.domain.model.rack.values.*;
import com.example.warehouseservice.infrastructure.http.dto.RackDto;
import com.example.warehouseservice.infrastructure.http.dto.RackToSaveDto;

class RackMapper {

    static RackDto toDto(Rack model) {
        return RackDto.builder()
                .id(model.getId().value())
                .name(model.getName().value())
                .createdDate(model.getCreatedDate().value())
                .updatedDate(model.getUpdatedDate().value())
                .shelvesCount(model.getShelvesCount().value())
                .build();
    }

    static Rack toModel(RackDto dto) {
        return Rack.builder()
                .id(new RackId(dto.getId()))
                .name(new RackName(dto.getName()))
                .createdDate(new RackCreatedDate(dto.getCreatedDate()))
                .updatedDate(new RackUpdatedDate(dto.getUpdatedDate()))
                .shelvesCount(new RackShelvesCount(dto.getShelvesCount()))
                .build();
    }

    static Rack toModel(RackToSaveDto dto) {
        return Rack.builder()
                .name(new RackName(dto.getName()))
                .build();
    }
}
