package com.example.statisticsservice.domain.model.genre;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private String name;
    private int loans;
}
