package com.example.libraryapp.domain.rack;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RackToUpdateDto extends RepresentationModel<RackToUpdateDto> {
    private String locationIdentifier;
}
