package com.example.warehouseservice.domain.exception;

import com.example.warehouseservice.domain.i18n.MessageKey;
import com.example.warehouseservice.domain.model.rack.values.RackId;

public class RackNotFoundException extends LibraryAppNotFoundException {

    public RackNotFoundException(RackId rackId) {
        super(MessageKey.RACK_NOT_FOUND_ID, rackId.value().toString());
    }

}
