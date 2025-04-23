package com.example.warehouseservice.domain.exception;

import com.example.warehouseservice.domain.MessageKey;
import com.example.warehouseservice.domain.exception.LibraryAppNotFoundException;
import com.example.warehouseservice.domain.model.shelf.ShelfId;

public class ShelfNotFoundException extends LibraryAppNotFoundException {

    public ShelfNotFoundException(ShelfId shelfId) {
        super(MessageKey.SHELF_NOT_FOUND_ID, shelfId.value().toString());
    }

}
