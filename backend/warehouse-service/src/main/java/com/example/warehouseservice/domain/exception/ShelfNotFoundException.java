package com.example.warehouseservice.domain.exception;

import com.example.warehouseservice.domain.i18n.MessageKey;
import com.example.warehouseservice.domain.model.shelf.values.ShelfId;

public class ShelfNotFoundException extends LibraryAppNotFoundException {

    public ShelfNotFoundException(ShelfId shelfId) {
        super(MessageKey.SHELF_NOT_FOUND_ID, shelfId.value().toString());
    }

}
