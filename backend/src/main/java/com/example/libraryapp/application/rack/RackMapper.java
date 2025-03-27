package com.example.libraryapp.application.rack;

import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.rack.dto.RackToSaveDto;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.rack.model.RackLocationId;

class RackMapper {

    static RackDto toDto(Rack model) {
        return RackDto.builder()
                .id(model.getId().value())
                .locationIdentifier(model.getLocationIdentifier().value())
                .build();
    }

    static Rack toModel(RackDto dto) {
        return Rack.builder()
                .id(new RackId(dto.getId()))
                .locationIdentifier(new RackLocationId(dto.getLocationIdentifier()))
                .build();
    }

    static Rack toModel(RackToSaveDto dto) {
        return Rack.builder()
                .locationIdentifier(new RackLocationId(dto.getLocationIdentifier()))
                .build();
    }
}
