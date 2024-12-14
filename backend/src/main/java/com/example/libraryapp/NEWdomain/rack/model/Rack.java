package com.example.libraryapp.NEWdomain.rack.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Rack {
    private Long id;
    private String locationIdentifier;
}
