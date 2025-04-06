package com.example.libraryapp.domain.rack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RackToSaveDto {
    private String name;
    private String location;
}
