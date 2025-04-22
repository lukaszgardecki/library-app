package com.example.libraryapp.domain.shelf.model;

import com.example.libraryapp.domain.rack.model.RackId;
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
