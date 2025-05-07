package com.example.warehouseservice.domain.ports.out;

import com.example.warehouseservice.domain.integration.catalog.dto.BookDto;
import com.example.warehouseservice.domain.integration.catalog.dto.BookItemDto;
import com.example.warehouseservice.domain.integration.catalog.BookId;
import com.example.warehouseservice.domain.integration.catalog.BookItemId;
import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.domain.model.shelf.values.ShelfId;

public interface CatalogServicePort {

    Long countBookItemsByParams(RackId rackId, ShelfId shelfId);

    BookItemDto getBookItemById(BookItemId bookItemId);

    BookDto getBookById(BookId bookId);
}
