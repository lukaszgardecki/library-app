package com.example.libraryapp.NEWdomain.rack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RackDto {
    private Long id;
    private String locationIdentifier;
}
