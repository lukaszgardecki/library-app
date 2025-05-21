package com.example.catalogservice.domain.integration.warehouse.shelf;

import com.example.catalogservice.domain.integration.warehouse.shelf.values.*;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
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
