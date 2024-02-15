package com.example.libraryapp.domain.rack;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RackDto extends RepresentationModel<RackDto> {
    private Long id;
    private String locationIdentifier;
    @JsonIgnore
    private List<BookItem> bookItems;
}
