package com.example.warehouseservice.domain.ports;

import com.example.warehouseservice.domain.dto.BookDto;
import com.example.warehouseservice.domain.dto.BookItemDto;
import com.example.warehouseservice.domain.model.BookId;
import com.example.warehouseservice.domain.model.BookItemId;
import com.example.warehouseservice.domain.model.rack.RackId;
import com.example.warehouseservice.domain.model.shelf.ShelfId;

public interface CatalogServicePort {

    Long countBookItemsByParams(RackId rackId, ShelfId shelfId);

    BookItemDto getBookItemById(BookItemId bookItemId);

    BookDto getBookById(BookId bookId);
}
