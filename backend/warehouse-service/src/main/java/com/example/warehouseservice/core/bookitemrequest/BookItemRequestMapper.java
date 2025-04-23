package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.domain.dto.WarehouseBookItemRequestListViewDto;
import com.example.warehouseservice.domain.model.WarehouseBookItemRequest;

class BookItemRequestMapper {

    static WarehouseBookItemRequestListViewDto toRequestListViewDto(WarehouseBookItemRequest model) {
        return new WarehouseBookItemRequestListViewDto(
                model.getBookItemRequest().getId(),
                model.getBookItemRequest().getStatus(),
                model.getBookItemRequest().getCreationDate(),
                model.getBook().getTitle(),
                model.getBookItem().getBarcode(),
                model.getBook().getFormat(),
                model.getRack().getName(),
                model.getShelf().getName()
        );
    }
}
