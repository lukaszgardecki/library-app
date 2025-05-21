package com.example.catalogservice.domain.integration.warehouse.rack;

import com.example.catalogservice.domain.integration.warehouse.rack.values.RackCreatedDate;
import com.example.catalogservice.domain.integration.warehouse.rack.values.RackName;
import com.example.catalogservice.domain.integration.warehouse.rack.values.RackShelvesCount;
import com.example.catalogservice.domain.integration.warehouse.rack.values.RackUpdatedDate;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Rack {
    private RackId id;
    private RackName name;
    private RackCreatedDate createdDate;
    private RackUpdatedDate updatedDate;
    private RackShelvesCount shelvesCount;
}
