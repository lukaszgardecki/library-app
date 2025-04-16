package com.example.libraryapp.domain.rack.model;

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
