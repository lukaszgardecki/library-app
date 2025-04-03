package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.shelf.dto.ShelfDto;
import com.example.libraryapp.domain.shelf.model.*;

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

//    static Rack toModel(RackToSaveDto dto) {
//        return Rack.builder()
//                .location(new RackLocationId(dto.getLocation()))
//                .name(new RackName(dto.getName()))
//                .build();
//    }
}
