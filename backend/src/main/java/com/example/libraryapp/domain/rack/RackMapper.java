package com.example.libraryapp.domain.rack;

public class RackMapper {

    public static RackDto map(Rack entity) {
        return RackDto.builder()
                .id(entity.getId())
                .locationIdentifier(entity.getLocationIdentifier())
                .build();
    }

    public static Rack map(RackToSaveDto rackToSave) {
        return Rack.builder()
                .locationIdentifier(rackToSave.getLocationIdentifier())
                .build();
    }
}
