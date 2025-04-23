package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.dto.RackDto;
import com.example.warehouseservice.domain.dto.RackToSaveDto;
import com.example.warehouseservice.domain.model.rack.*;

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
