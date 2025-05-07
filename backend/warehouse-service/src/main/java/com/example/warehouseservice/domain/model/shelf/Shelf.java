package com.example.warehouseservice.domain.model.shelf;

import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.domain.model.shelf.values.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Shelf {
    private ShelfId id;
    private ShelfName name;
    private ShelfPosition position;
    private ShelfCreatedDate createdDate;
    private ShelfUpdatedDate updatedDate;
    private RackId rackId;
    private BookItemsCount bookItemsCount;
}
