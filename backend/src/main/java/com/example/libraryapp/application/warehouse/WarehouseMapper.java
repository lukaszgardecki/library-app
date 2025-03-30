package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.warehouse.dto.WarehouseBookItemRequestListViewDto;
import com.example.libraryapp.domain.warehouse.model.WarehouseBookItemRequest;

class WarehouseMapper {

    static WarehouseBookItemRequestListViewDto toRequestListViewDto(WarehouseBookItemRequest model) {
        return new WarehouseBookItemRequestListViewDto(
                model.getBookItemRequest().getId(),
                model.getBookItemRequest().getStatus(),
                model.getBookItemRequest().getCreationDate(),
                model.getBook().getTitle(),
                model.getBookItem().getBarcode(),
                model.getBookItem().getFormat(),
                model.getRack().getLocationIdentifier()
        );
    }
}
