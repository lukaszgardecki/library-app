package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.dto.ShelfDto;
import com.example.warehouseservice.domain.dto.ShelfToSaveDto;
import com.example.warehouseservice.domain.model.rack.RackId;
import com.example.warehouseservice.domain.model.shelf.*;

class ShelfMapper {

    static ShelfDto toDto(Shelf model) {
        return ShelfDto.builder()
                .id(model.getId().value())
                .name(model.getName().value())
                .position(model.getPosition().value())
                .createdDate(model.getCreatedDate().value())
                .updatedDate(model.getUpdatedDate().value())
                .bookItemsCount(model.getBookItemsCount().value())
                .build();
    }

    static Shelf toModel(ShelfDto dto) {
        return Shelf.builder()
                .id(new ShelfId(dto.getId()))
                .name(new ShelfName(dto.getName()))
                .position(new ShelfPosition(dto.getPosition()))
                .createdDate(new ShelfCreatedDate(dto.getCreatedDate()))
                .updatedDate(new ShelfUpdatedDate(dto.getUpdatedDate()))
                .bookItemsCount(new BookItemsCount(dto.getBookItemsCount()))
                .build();
    }

    static Shelf toModel(ShelfToSaveDto dto) {
        return Shelf.builder()
                .name(new ShelfName(dto.getName()))
                .rackId(new RackId(dto.getRackId()))
                .build();
    }
}
