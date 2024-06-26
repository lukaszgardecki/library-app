package com.example.libraryapp.domain.action;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionDto extends RepresentationModel<ActionDto> {
    private Long id;
    private Long memberId;
    private String type;
    private String message;
    private LocalDateTime createdAt;
}
