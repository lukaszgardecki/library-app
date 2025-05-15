package com.example.catalogservice.infrastructure.integration.warehouseservice;

import com.example.catalogservice.domain.integration.warehouse.rack.Rack;
import com.example.catalogservice.domain.integration.warehouse.rack.values.RackCreatedDate;
import com.example.catalogservice.domain.integration.warehouse.rack.values.RackName;
import com.example.catalogservice.domain.integration.warehouse.rack.values.RackShelvesCount;
import com.example.catalogservice.domain.integration.warehouse.rack.values.RackUpdatedDate;
import com.example.catalogservice.domain.integration.warehouse.shelf.Shelf;
import com.example.catalogservice.domain.integration.warehouse.shelf.values.*;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
import com.example.catalogservice.infrastructure.integration.warehouseservice.dto.RackDto;
import com.example.catalogservice.infrastructure.integration.warehouseservice.dto.ShelfDto;

class WarehouseMapper {

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
}
