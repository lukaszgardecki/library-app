package com.example.libraryapp.NEWapplication.rack;

import com.example.libraryapp.NEWdomain.rack.dto.RackDto;
import com.example.libraryapp.NEWdomain.rack.dto.RackToSaveDto;
import com.example.libraryapp.NEWdomain.rack.model.Rack;

class RackMapper {

    static RackDto toDto(Rack model) {
        return RackDto.builder()
                .id(model.getId())
                .locationIdentifier(model.getLocationIdentifier())
                .build();
    }

    static Rack toModel(RackDto dto) {
        return Rack.builder()
                .id(dto.getId())
                .locationIdentifier(dto.getLocationIdentifier())
                .build();
    }

    static Rack toModel(RackToSaveDto dto) {
        return Rack.builder()
                .locationIdentifier(dto.getLocationIdentifier())
                .build();
    }
}
